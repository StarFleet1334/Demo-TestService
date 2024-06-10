import http from 'k6/http';
import { sleep, check } from 'k6';

export default function () {
    const res = http.get('https://test.k6.io');
    sleep(1);

    // Check for response and log errors
    check(res, { 'status is 200': (r) => r.status === 200 });

    // Call the Spring Boot service endpoint
    const publishRes = http.get('http://localhost:9001/api/publish-test-type');
    check(publishRes, { 'status is 200': (r) => r.status === 200 });
    console.log('Publish Response here: ' + publishRes.status);
}

export function handleSummary(data) {
    let formattedSummary = {
        timestamp: new Date().toISOString(),
        metrics: {
            vus: data.metrics.vus.values.value,
            dataSent: data.metrics.data_sent.values.count,
            httpReqFailed: data.metrics.http_req_failed.values.fails,
            httpReqDurationAvg: data.metrics["http_req_duration{expected_response:true}"].values.avg,
            httpReqDurationMin: data.metrics["http_req_duration{expected_response:true}"].values.min,
            httpReqDurationMax: data.metrics["http_req_duration{expected_response:true}"].values.max,
            httpReqDurationP90: data.metrics["http_req_duration{expected_response:true}"].values["p(90)"],
            httpReqDurationP95: data.metrics["http_req_duration{expected_response:true}"].values["p(95)"]
        },
        checks: data.root_group.checks.map(check => ({
            name: check.name,
            passes: check.passes,
            fails: check.fails
        }))
    };

    // Convert the JSON to a readable string format
    let summary = JSON.stringify(formattedSummary, null, 2)
        .replace(/\\n/g, '')  // Remove new line characters
        .replace(/\\"/g, ''); // Remove escaped double quotes

    // Clean up the string
    summary = summary.replace(/},\s*{/g, '}, {')  // Ensure objects are clearly separated
        .replace(/"\s*,\s*"/g, '", "')  // Ensure comma separation is clear
        .replace(/\s*"\s*:\s*/g, '": '); // Ensure key-value pairs are clear

    console.log('Formatted Summary:', summary);

    // Send the summary to Spring Boot service endpoint
    const url = 'http://localhost:9001/api/log-summary';
    const payload = summary;
    const params = { headers: { 'Content-Type': 'application/json' } };
    http.post(url, payload, params);

    return {
        stdout: summary,  // Show the summary in the console
    };
}
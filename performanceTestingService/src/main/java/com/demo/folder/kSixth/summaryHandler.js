import http from 'k6/http';

// Function to handle and post summary data
export function postSummary(data, endpointUrl) {
    let summary = JSON.stringify(data, null, 2)
        .replace(/\\n/g, '')
        .replace(/\\"/g, '');

    summary = summary.replace(/},\s*{/g, '}, {')
        .replace(/"\s*,\s*"/g, '", "')
        .replace(/\s*"\s*:\s*/g, '": ');

    console.log('Formatted Summary:', summary);

    // Send the summary to a Spring Boot service endpoint
    http.post(endpointUrl, summary, { headers: { 'Content-Type': 'application/json' } });
}

export function formatSummary(data) {
    return {
        timestamp: new Date().toISOString(),
        metrics: {
            vus: data.metrics.vus && data.metrics.vus.values ? data.metrics.vus.values.value : null,
            dataSent: data.metrics.data_sent && data.metrics.data_sent.values ? data.metrics.data_sent.values.count : null,
            httpReqFailed: data.metrics.http_req_failed && data.metrics.http_req_failed.values ? data.metrics.http_req_failed.values.fails : null,
            httpReqDurationAvg: data.metrics["http_req_duration{expected_response:true}"] && data.metrics["http_req_duration{expected_response:true}"].values ? data.metrics["http_req_duration{expected_response:true}"].values.avg : null,
            httpReqDurationMin: data.metrics["http_req_duration{expected_response:true}"] && data.metrics["http_req_duration{expected_response:true}"].values ? data.metrics["http_req_duration{expected_response:true}"].values.min : null,
            httpReqDurationMax: data.metrics["http_req_duration{expected_response:true}"] && data.metrics["http_req_duration{expected_response:true}"].values ? data.metrics["http_req_duration{expected_response:true}"].values.max : null,
            httpReqDurationP90: data.metrics["http_req_duration{expected_response:true}"] && data.metrics["http_req_duration{expected_response:true}"].values ? data.metrics["http_req_duration{expected_response:true}"].values["p(90)"] : null,
            httpReqDurationP95: data.metrics["http_req_duration{expected_response:true}"] && data.metrics["http_req_duration{expected_response:true}"].values ? data.metrics["http_req_duration{expected_response:true}"].values["p(95)"] : null,
        },
        checks: data.root_group && data.root_group.checks ? data.root_group.checks.map(check => ({
            name: check.name,
            passes: check.passes,
            fails: check.fails
        })) : []
    };
}
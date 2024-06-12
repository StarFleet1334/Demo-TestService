import http from 'k6/http';
import { check, sleep, group } from 'k6';
import { Rate } from 'k6/metrics';

let errorRate = new Rate('errors');

export let options = {
    thresholds: {
        'errors': ['rate<0.01'],  // Fail the test if the error rate is higher than 1%
        'http_req_duration': ['p(95)<500']  // 95% of requests must complete below 500ms
    },
};

function logFailure(message) {
    const failureDetails = {
        level: "ERROR",
        message: message,
        testName: "k6 test",
        className: "failureTest.js",
        startTime: new Date().toISOString() // Ensure the start time is captured at the moment of failure
    };
    console.log(JSON.stringify(failureDetails)); // Log to the console in JSON format
    return failureDetails;
}

function sendFailureDetails(details) {
    let url = 'http://localhost:9001/api/test_failure'; // Make sure this URL is correct and reachable
    let payload = JSON.stringify(details);
    let params = {
        headers: {
            'Content-Type': 'application/json'
        },
    };

    let res = http.post(url, payload, params);
    check(res, {
        'is status 200': (r) => r.status === 200  // Ensure the server responds with HTTP 200 OK
    });
}

export default function () {
    group("API Testing", function () {
        let res = http.get('https://test.k6.ios'); // Ensure the URL is correct
        let result = check(res, {
            'status is 200': (r) => r.status === 200
        });

        if (!result) {
            errorRate.add(1);
            let failureDetails = logFailure(`Request failed with status ${res.status}`);
            sendFailureDetails(failureDetails);
        }
    });

    sleep(1);
}

export function handleSummary(data) {
    let summaryDetails = {};
    if (data.metrics.http_req_duration.values['p(95)'] > 500) {
        summaryDetails = logFailure('Performance threshold exceeded');
        sendFailureDetails(summaryDetails);
    }
    if (data.metrics.errors.rate > 0.01) {
        summaryDetails = logFailure('Error rate threshold exceeded');
        sendFailureDetails(summaryDetails);
    }
    return {};
}
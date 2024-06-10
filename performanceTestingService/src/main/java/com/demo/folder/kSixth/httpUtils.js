import http from 'k6/http';
import { check } from 'k6';

// Function to perform a GET request and check the status
export function performGetRequest(url, expectedStatus) {
    let response = http.get(url);
    check(response, {
        [`status is ${expectedStatus}`]: (r) => r.status === expectedStatus
    });
    return response;
}
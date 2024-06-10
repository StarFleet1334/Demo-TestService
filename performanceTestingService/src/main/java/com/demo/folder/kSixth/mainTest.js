import { performGetRequest } from './httpUtils.js';
import { postSummary, formatSummary } from './summaryHandler.js';

const testUrl = 'https://test.k6.io';
const publishUrl = 'http://localhost:9001/api/publish-test-type';
const logSummaryUrl = 'http://localhost:9001/api/log-summary';

export default function () {
    const res = performGetRequest(testUrl, 200);
    console.log('Publish Response here: ' + res.status);

    const publishRes = performGetRequest(publishUrl, 200);
    console.log('Publish Response here: ' + publishRes.status);
}

export function handleSummary(data, logUrl) {
    let formattedData = formatSummary(data);
    postSummary(formattedData, logUrl);
}

// Usage example
// handleSummary(data, logSummaryUrl);
import http from 'k6/http';
import { sleep } from 'k6';

export default function () {
    const res = http.get('https://test.k6.io');
    sleep(1);

    // Call the Spring Boot service endpoint
    const publishRes = http.get('http://localhost:9001/api/publish-test-type');
    console.log('Publish Response here: ' + publishRes.status);
}
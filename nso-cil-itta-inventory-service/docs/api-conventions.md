# API conventions

* Base path `/api/v1/<plural-kebab-entity>`
* `GET /` search with `q`, per attribute filters, `page`, `size`, `sort=field,asc|desc`
* `GET /{id}`, `GET /by-code/{code}`, `HEAD /by-code/{code}`
* `POST /` create (201 + `Location`), `PUT /{id}` partial update, `DELETE /{id}` (204)
* `PATCH /{id}/status?status=ACTIVE` lifecycle transition
* `POST /bulk` and `POST /bulk/status` return `{ succeeded: [], failures: [{ index, message }] }`
* `GET /stats/status` counts per lifecycle status, `GET /export` CSV
* Errors: 400 bean validation, 404 not found, 409 duplicate code or stale version, 422 business rule

"""Search filter for Country and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.geography.country.schemas import *  # noqa: F401,F403


class CountryFilter(ApiModel):
    """Query-string filter for Country search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    iso_code: str | None = Field(None, description="Iso Code contains")
    iso_numeric_min: int | None = None
    iso_numeric_max: int | None = None
    dial_code: str | None = Field(None, description="Dial Code contains")
    currency: str | None = Field(None, description="Currency contains")
    regulatory_body: str | None = Field(None, description="Regulatory Body contains")
    timezone: str | None = Field(None, description="Timezone contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[CountryCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: CountryFilter) -> dict[str, str]:
    """Translate a filter into the query parameters understood by the inventory service."""
    params: dict[str, str] = {}
    if flt.q and flt.q.strip():
        params["q"] = flt.q.strip()
    if flt.status:
        params["status"] = ",".join(v.value for v in flt.status)
    if flt.code:
        params["code"] = flt.code.strip()
    if flt.name:
        params["name"] = flt.name.strip()
    if flt.external_ref:
        params["externalRef"] = flt.external_ref.strip()
    if flt.iso_code:
        params["isoCode"] = flt.iso_code.strip()
    if flt.iso_numeric_min is not None:
        params["isoNumericMin"] = str(flt.iso_numeric_min)
    if flt.iso_numeric_max is not None:
        params["isoNumericMax"] = str(flt.iso_numeric_max)
    if flt.dial_code:
        params["dialCode"] = flt.dial_code.strip()
    if flt.currency:
        params["currency"] = flt.currency.strip()
    if flt.regulatory_body:
        params["regulatoryBody"] = flt.regulatory_body.strip()
    if flt.timezone:
        params["timezone"] = flt.timezone.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    return params

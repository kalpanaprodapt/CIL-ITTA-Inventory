"""Search filter for Software Version and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.software.software_version.schemas import *  # noqa: F401,F403


class SoftwareVersionFilter(ApiModel):
    """Query-string filter for Software Version search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    image_id: UUID | None = None
    version_string: str | None = Field(None, description="Version String contains")
    release_date_from: date | None = None
    release_date_to: date | None = None
    end_of_support_from: date | None = None
    end_of_support_to: date | None = None
    release_train: list[SoftwareVersionReleaseTrain] | None = None
    cve_count_min: int | None = None
    cve_count_max: int | None = None
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[SoftwareVersionCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: SoftwareVersionFilter) -> dict[str, str]:
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
    if flt.image_id is not None:
        params["imageId"] = str(flt.image_id)
    if flt.version_string:
        params["versionString"] = flt.version_string.strip()
    if flt.release_date_from is not None:
        params["releaseDateFrom"] = flt.release_date_from.isoformat()
    if flt.release_date_to is not None:
        params["releaseDateTo"] = flt.release_date_to.isoformat()
    if flt.end_of_support_from is not None:
        params["endOfSupportFrom"] = flt.end_of_support_from.isoformat()
    if flt.end_of_support_to is not None:
        params["endOfSupportTo"] = flt.end_of_support_to.isoformat()
    if flt.release_train:
        params["releaseTrain"] = ",".join(v.value for v in flt.release_train)
    if flt.cve_count_min is not None:
        params["cveCountMin"] = str(flt.cve_count_min)
    if flt.cve_count_max is not None:
        params["cveCountMax"] = str(flt.cve_count_max)
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    return params

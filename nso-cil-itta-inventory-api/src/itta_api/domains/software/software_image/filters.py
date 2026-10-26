"""Search filter for Software Image and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.software.software_image.schemas import *  # noqa: F401,F403


class SoftwareImageFilter(ApiModel):
    """Query-string filter for Software Image search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    vendor_id: UUID | None = None
    image_name: str | None = Field(None, description="Image Name contains")
    size_mb_min: int | None = None
    size_mb_max: int | None = None
    checksum: str | None = Field(None, description="Checksum contains")
    platform: str | None = Field(None, description="Platform contains")
    storage_path: str | None = Field(None, description="Storage Path contains")
    lifecycle_stage: list[SoftwareImageLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[SoftwareImageCriticality] | None = None


def to_query(flt: SoftwareImageFilter) -> dict[str, str]:
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
    if flt.vendor_id is not None:
        params["vendorId"] = str(flt.vendor_id)
    if flt.image_name:
        params["imageName"] = flt.image_name.strip()
    if flt.size_mb_min is not None:
        params["sizeMbMin"] = str(flt.size_mb_min)
    if flt.size_mb_max is not None:
        params["sizeMbMax"] = str(flt.size_mb_max)
    if flt.checksum:
        params["checksum"] = flt.checksum.strip()
    if flt.platform:
        params["platform"] = flt.platform.strip()
    if flt.storage_path:
        params["storagePath"] = flt.storage_path.strip()
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    return params

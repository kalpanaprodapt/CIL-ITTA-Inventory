"""Search filter for Policy Document and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.compliance.policy_document.schemas import *  # noqa: F401,F403


class PolicyDocumentFilter(ApiModel):
    """Query-string filter for Policy Document search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    title: str | None = Field(None, description="Title contains")
    doc_version: str | None = Field(None, description="Doc Version contains")
    effective_date_from: date | None = None
    effective_date_to: date | None = None
    owner: str | None = Field(None, description="Owner contains")
    document_url: str | None = Field(None, description="Document Url contains")
    lifecycle_stage: list[PolicyDocumentLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    criticality: list[PolicyDocumentCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")


def to_query(flt: PolicyDocumentFilter) -> dict[str, str]:
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
    if flt.title:
        params["title"] = flt.title.strip()
    if flt.doc_version:
        params["docVersion"] = flt.doc_version.strip()
    if flt.effective_date_from is not None:
        params["effectiveDateFrom"] = flt.effective_date_from.isoformat()
    if flt.effective_date_to is not None:
        params["effectiveDateTo"] = flt.effective_date_to.isoformat()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    if flt.document_url:
        params["documentUrl"] = flt.document_url.strip()
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    return params

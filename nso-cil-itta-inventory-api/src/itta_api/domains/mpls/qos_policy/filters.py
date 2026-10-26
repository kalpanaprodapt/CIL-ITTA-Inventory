"""Search filter for Qos Policy and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.mpls.qos_policy.schemas import *  # noqa: F401,F403


class QosPolicyFilter(ApiModel):
    """Query-string filter for Qos Policy search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    policy_name: str | None = Field(None, description="Policy Name contains")
    class_count_min: int | None = None
    class_count_max: int | None = None
    cir_mbps_min: int | None = None
    cir_mbps_max: int | None = None
    pir_mbps_min: int | None = None
    pir_mbps_max: int | None = None
    marking_scheme: str | None = Field(None, description="Marking Scheme contains")
    labels: str | None = Field(None, description="Labels contains")
    lifecycle_stage: list[QosPolicyLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")


def to_query(flt: QosPolicyFilter) -> dict[str, str]:
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
    if flt.policy_name:
        params["policyName"] = flt.policy_name.strip()
    if flt.class_count_min is not None:
        params["classCountMin"] = str(flt.class_count_min)
    if flt.class_count_max is not None:
        params["classCountMax"] = str(flt.class_count_max)
    if flt.cir_mbps_min is not None:
        params["cirMbpsMin"] = str(flt.cir_mbps_min)
    if flt.cir_mbps_max is not None:
        params["cirMbpsMax"] = str(flt.cir_mbps_max)
    if flt.pir_mbps_min is not None:
        params["pirMbpsMin"] = str(flt.pir_mbps_min)
    if flt.pir_mbps_max is not None:
        params["pirMbpsMax"] = str(flt.pir_mbps_max)
    if flt.marking_scheme:
        params["markingScheme"] = flt.marking_scheme.strip()
    if flt.labels:
        params["labels"] = flt.labels.strip()
    if flt.lifecycle_stage:
        params["lifecycleStage"] = ",".join(v.value for v in flt.lifecycle_stage)
    if flt.source_system:
        params["sourceSystem"] = flt.source_system.strip()
    if flt.region:
        params["region"] = flt.region.strip()
    if flt.owner:
        params["owner"] = flt.owner.strip()
    return params

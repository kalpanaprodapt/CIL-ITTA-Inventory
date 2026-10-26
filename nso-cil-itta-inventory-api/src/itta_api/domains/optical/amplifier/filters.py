"""Search filter for Amplifier and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.optical.amplifier.schemas import *  # noqa: F401,F403


class AmplifierFilter(ApiModel):
    """Query-string filter for Amplifier search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    system_id: UUID | None = None
    amp_type: list[AmplifierAmpType] | None = None
    gain_db_min: Decimal | None = None
    gain_db_max: Decimal | None = None
    output_power_dbm_min: Decimal | None = None
    output_power_dbm_max: Decimal | None = None
    noise_figure_db_min: Decimal | None = None
    noise_figure_db_max: Decimal | None = None
    lifecycle_stage: list[AmplifierLifecycleStage] | None = None
    source_system: str | None = Field(None, description="Source System contains")
    region: str | None = Field(None, description="Region contains")
    owner: str | None = Field(None, description="Owner contains")
    criticality: list[AmplifierCriticality] | None = None


def to_query(flt: AmplifierFilter) -> dict[str, str]:
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
    if flt.system_id is not None:
        params["systemId"] = str(flt.system_id)
    if flt.amp_type:
        params["ampType"] = ",".join(v.value for v in flt.amp_type)
    if flt.gain_db_min is not None:
        params["gainDbMin"] = str(flt.gain_db_min)
    if flt.gain_db_max is not None:
        params["gainDbMax"] = str(flt.gain_db_max)
    if flt.output_power_dbm_min is not None:
        params["outputPowerDbmMin"] = str(flt.output_power_dbm_min)
    if flt.output_power_dbm_max is not None:
        params["outputPowerDbmMax"] = str(flt.output_power_dbm_max)
    if flt.noise_figure_db_min is not None:
        params["noiseFigureDbMin"] = str(flt.noise_figure_db_min)
    if flt.noise_figure_db_max is not None:
        params["noiseFigureDbMax"] = str(flt.noise_figure_db_max)
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

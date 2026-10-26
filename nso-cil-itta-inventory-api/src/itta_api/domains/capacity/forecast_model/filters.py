"""Search filter for Forecast Model and its translation to upstream query parameters."""
from __future__ import annotations

from datetime import date, datetime
from decimal import Decimal
from uuid import UUID

from pydantic import Field

from itta_core.models import ApiModel, LifecycleStatus
from itta_api.domains.capacity.forecast_model.schemas import *  # noqa: F401,F403


class ForecastModelFilter(ApiModel):
    """Query-string filter for Forecast Model search."""

    q: str | None = Field(None, description="Free text over code, name and description")
    status: list[LifecycleStatus] | None = None
    code: str | None = Field(None, description="Code contains")
    name: str | None = Field(None, description="Name contains")
    external_ref: str | None = Field(None, description="External Ref contains")
    model_name: str | None = Field(None, description="Model Name contains")
    algorithm: list[ForecastModelAlgorithm] | None = None
    trained_on_from: datetime | None = None
    trained_on_to: datetime | None = None
    mape_min: Decimal | None = None
    mape_max: Decimal | None = None
    criticality: list[ForecastModelCriticality] | None = None
    cost_center: str | None = Field(None, description="Cost Center contains")
    effective_from_from: date | None = None
    effective_from_to: date | None = None
    effective_to_from: date | None = None
    effective_to_to: date | None = None


def to_query(flt: ForecastModelFilter) -> dict[str, str]:
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
    if flt.model_name:
        params["modelName"] = flt.model_name.strip()
    if flt.algorithm:
        params["algorithm"] = ",".join(v.value for v in flt.algorithm)
    if flt.trained_on_from is not None:
        params["trainedOnFrom"] = flt.trained_on_from.isoformat()
    if flt.trained_on_to is not None:
        params["trainedOnTo"] = flt.trained_on_to.isoformat()
    if flt.mape_min is not None:
        params["mapeMin"] = str(flt.mape_min)
    if flt.mape_max is not None:
        params["mapeMax"] = str(flt.mape_max)
    if flt.criticality:
        params["criticality"] = ",".join(v.value for v in flt.criticality)
    if flt.cost_center:
        params["costCenter"] = flt.cost_center.strip()
    if flt.effective_from_from is not None:
        params["effectiveFromFrom"] = flt.effective_from_from.isoformat()
    if flt.effective_from_to is not None:
        params["effectiveFromTo"] = flt.effective_from_to.isoformat()
    if flt.effective_to_from is not None:
        params["effectiveToFrom"] = flt.effective_to_from.isoformat()
    if flt.effective_to_to is not None:
        params["effectiveToTo"] = flt.effective_to_to.isoformat()
    return params

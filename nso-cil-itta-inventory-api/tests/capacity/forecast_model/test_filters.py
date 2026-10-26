"""Filter translation tests for Forecast Model."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.capacity.forecast_model.filters import ForecastModelFilter, to_query
from itta_api.domains.capacity.forecast_model.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(ForecastModelFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(ForecastModelFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(ForecastModelFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(ForecastModelFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(ForecastModelFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(ForecastModelFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(ForecastModelFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(ForecastModelFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(ForecastModelFilter(external_ref=""))


def test_model_name_is_trimmed_into_query():
    assert to_query(ForecastModelFilter(model_name="  abc ")) == {"modelName": "abc"}


def test_model_name_blank_is_omitted():
    assert "modelName" not in to_query(ForecastModelFilter(model_name=""))


def test_algorithm_values_are_comma_joined():
    members = list(ForecastModelAlgorithm)[:2]
    query = to_query(ForecastModelFilter(algorithm=members))
    assert query["algorithm"] == ",".join(m.value for m in members)


def test_trained_on_window_is_iso_formatted():
    query = to_query(ForecastModelFilter(trained_on_from=datetime(2026, 1, 1, tzinfo=timezone.utc), trained_on_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["trainedOnFrom"].startswith("2026-01-01")
    assert query["trainedOnTo"].startswith("2026-01-01")


def test_mape_range_is_sent():
    query = to_query(ForecastModelFilter(mape_min=Decimal("1.5"), mape_max=Decimal("9.5")))
    assert query["mapeMin"] == str(Decimal("1.5"))
    assert query["mapeMax"] == str(Decimal("9.5"))


def test_mape_zero_is_not_dropped():
    assert to_query(ForecastModelFilter(mape_min=0))["mapeMin"] == "0"


def test_criticality_values_are_comma_joined():
    members = list(ForecastModelCriticality)[:2]
    query = to_query(ForecastModelFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(ForecastModelFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(ForecastModelFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(ForecastModelFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(ForecastModelFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


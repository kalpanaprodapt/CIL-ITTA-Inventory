"""Filter translation tests for Utilization Sample."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.capacity.utilization_sample.filters import UtilizationSampleFilter, to_query
from itta_api.domains.capacity.utilization_sample.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(UtilizationSampleFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(UtilizationSampleFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(UtilizationSampleFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(UtilizationSampleFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(UtilizationSampleFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(UtilizationSampleFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(UtilizationSampleFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(UtilizationSampleFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(UtilizationSampleFilter(external_ref=""))


def test_target_ref_is_trimmed_into_query():
    assert to_query(UtilizationSampleFilter(target_ref="  abc ")) == {"targetRef": "abc"}


def test_target_ref_blank_is_omitted():
    assert "targetRef" not in to_query(UtilizationSampleFilter(target_ref=""))


def test_metric_is_trimmed_into_query():
    assert to_query(UtilizationSampleFilter(metric="  abc ")) == {"metric": "abc"}


def test_metric_blank_is_omitted():
    assert "metric" not in to_query(UtilizationSampleFilter(metric=""))


def test_sampled_at_window_is_iso_formatted():
    query = to_query(UtilizationSampleFilter(sampled_at_from=datetime(2026, 1, 1, tzinfo=timezone.utc), sampled_at_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["sampledAtFrom"].startswith("2026-01-01")
    assert query["sampledAtTo"].startswith("2026-01-01")


def test_value_pct_range_is_sent():
    query = to_query(UtilizationSampleFilter(value_pct_min=Decimal("1.5"), value_pct_max=Decimal("9.5")))
    assert query["valuePctMin"] == str(Decimal("1.5"))
    assert query["valuePctMax"] == str(Decimal("9.5"))


def test_value_pct_zero_is_not_dropped():
    assert to_query(UtilizationSampleFilter(value_pct_min=0))["valuePctMin"] == "0"


def test_peak_is_lowercase_boolean():
    assert to_query(UtilizationSampleFilter(peak=True))["peak"] == "true"
    assert to_query(UtilizationSampleFilter(peak=False))["peak"] == "false"


def test_lifecycle_stage_values_are_comma_joined():
    members = list(UtilizationSampleLifecycleStage)[:2]
    query = to_query(UtilizationSampleFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(UtilizationSampleFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(UtilizationSampleFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(UtilizationSampleFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(UtilizationSampleFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(UtilizationSampleFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(UtilizationSampleFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(UtilizationSampleCriticality)[:2]
    query = to_query(UtilizationSampleFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


"""Filter translation tests for Path Trace."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.topology.path_trace.filters import PathTraceFilter, to_query
from itta_api.domains.topology.path_trace.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(PathTraceFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(PathTraceFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(PathTraceFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(PathTraceFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(PathTraceFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(PathTraceFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(PathTraceFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(PathTraceFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(PathTraceFilter(external_ref=""))


def test_source_ref_is_trimmed_into_query():
    assert to_query(PathTraceFilter(source_ref="  abc ")) == {"sourceRef": "abc"}


def test_source_ref_blank_is_omitted():
    assert "sourceRef" not in to_query(PathTraceFilter(source_ref=""))


def test_target_ref_is_trimmed_into_query():
    assert to_query(PathTraceFilter(target_ref="  abc ")) == {"targetRef": "abc"}


def test_target_ref_blank_is_omitted():
    assert "targetRef" not in to_query(PathTraceFilter(target_ref=""))


def test_hop_count_range_is_sent():
    query = to_query(PathTraceFilter(hop_count_min=1, hop_count_max=9))
    assert query["hopCountMin"] == str(1)
    assert query["hopCountMax"] == str(9)


def test_hop_count_zero_is_not_dropped():
    assert to_query(PathTraceFilter(hop_count_min=0))["hopCountMin"] == "0"


def test_total_latency_ms_range_is_sent():
    query = to_query(PathTraceFilter(total_latency_ms_min=Decimal("1.5"), total_latency_ms_max=Decimal("9.5")))
    assert query["totalLatencyMsMin"] == str(Decimal("1.5"))
    assert query["totalLatencyMsMax"] == str(Decimal("9.5"))


def test_total_latency_ms_zero_is_not_dropped():
    assert to_query(PathTraceFilter(total_latency_ms_min=0))["totalLatencyMsMin"] == "0"


def test_trace_method_is_trimmed_into_query():
    assert to_query(PathTraceFilter(trace_method="  abc ")) == {"traceMethod": "abc"}


def test_trace_method_blank_is_omitted():
    assert "traceMethod" not in to_query(PathTraceFilter(trace_method=""))


def test_traced_at_window_is_iso_formatted():
    query = to_query(PathTraceFilter(traced_at_from=datetime(2026, 1, 1, tzinfo=timezone.utc), traced_at_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["tracedAtFrom"].startswith("2026-01-01")
    assert query["tracedAtTo"].startswith("2026-01-01")


def test_lifecycle_stage_values_are_comma_joined():
    members = list(PathTraceLifecycleStage)[:2]
    query = to_query(PathTraceFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(PathTraceFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(PathTraceFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(PathTraceFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(PathTraceFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(PathTraceFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(PathTraceFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(PathTraceCriticality)[:2]
    query = to_query(PathTraceFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


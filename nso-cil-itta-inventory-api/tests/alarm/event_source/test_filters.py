"""Filter translation tests for Event Source."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.alarm.event_source.filters import EventSourceFilter, to_query
from itta_api.domains.alarm.event_source.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(EventSourceFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(EventSourceFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(EventSourceFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(EventSourceFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(EventSourceFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(EventSourceFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(EventSourceFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(EventSourceFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(EventSourceFilter(external_ref=""))


def test_source_name_is_trimmed_into_query():
    assert to_query(EventSourceFilter(source_name="  abc ")) == {"sourceName": "abc"}


def test_source_name_blank_is_omitted():
    assert "sourceName" not in to_query(EventSourceFilter(source_name=""))


def test_protocol_values_are_comma_joined():
    members = list(EventSourceProtocol)[:2]
    query = to_query(EventSourceFilter(protocol=members))
    assert query["protocol"] == ",".join(m.value for m in members)


def test_endpoint_is_trimmed_into_query():
    assert to_query(EventSourceFilter(endpoint="  abc ")) == {"endpoint": "abc"}


def test_endpoint_blank_is_omitted():
    assert "endpoint" not in to_query(EventSourceFilter(endpoint=""))


def test_heartbeat_seconds_range_is_sent():
    query = to_query(EventSourceFilter(heartbeat_seconds_min=1, heartbeat_seconds_max=9))
    assert query["heartbeatSecondsMin"] == str(1)
    assert query["heartbeatSecondsMax"] == str(9)


def test_heartbeat_seconds_zero_is_not_dropped():
    assert to_query(EventSourceFilter(heartbeat_seconds_min=0))["heartbeatSecondsMin"] == "0"


def test_labels_is_trimmed_into_query():
    assert to_query(EventSourceFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(EventSourceFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(EventSourceLifecycleStage)[:2]
    query = to_query(EventSourceFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(EventSourceFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(EventSourceFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(EventSourceFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(EventSourceFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(EventSourceFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(EventSourceFilter(owner=""))


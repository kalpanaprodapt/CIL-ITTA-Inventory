"""Filter translation tests for Sla Profile."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.customer.sla_profile.filters import SlaProfileFilter, to_query
from itta_api.domains.customer.sla_profile.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(SlaProfileFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(SlaProfileFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(SlaProfileFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(SlaProfileFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(SlaProfileFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(SlaProfileFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(SlaProfileFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(SlaProfileFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(SlaProfileFilter(external_ref=""))


def test_profile_name_is_trimmed_into_query():
    assert to_query(SlaProfileFilter(profile_name="  abc ")) == {"profileName": "abc"}


def test_profile_name_blank_is_omitted():
    assert "profileName" not in to_query(SlaProfileFilter(profile_name=""))


def test_availability_pct_range_is_sent():
    query = to_query(SlaProfileFilter(availability_pct_min=Decimal("1.5"), availability_pct_max=Decimal("9.5")))
    assert query["availabilityPctMin"] == str(Decimal("1.5"))
    assert query["availabilityPctMax"] == str(Decimal("9.5"))


def test_availability_pct_zero_is_not_dropped():
    assert to_query(SlaProfileFilter(availability_pct_min=0))["availabilityPctMin"] == "0"


def test_mttr_minutes_range_is_sent():
    query = to_query(SlaProfileFilter(mttr_minutes_min=1, mttr_minutes_max=9))
    assert query["mttrMinutesMin"] == str(1)
    assert query["mttrMinutesMax"] == str(9)


def test_mttr_minutes_zero_is_not_dropped():
    assert to_query(SlaProfileFilter(mttr_minutes_min=0))["mttrMinutesMin"] == "0"


def test_latency_ms_range_is_sent():
    query = to_query(SlaProfileFilter(latency_ms_min=Decimal("1.5"), latency_ms_max=Decimal("9.5")))
    assert query["latencyMsMin"] == str(Decimal("1.5"))
    assert query["latencyMsMax"] == str(Decimal("9.5"))


def test_latency_ms_zero_is_not_dropped():
    assert to_query(SlaProfileFilter(latency_ms_min=0))["latencyMsMin"] == "0"


def test_jitter_ms_range_is_sent():
    query = to_query(SlaProfileFilter(jitter_ms_min=Decimal("1.5"), jitter_ms_max=Decimal("9.5")))
    assert query["jitterMsMin"] == str(Decimal("1.5"))
    assert query["jitterMsMax"] == str(Decimal("9.5"))


def test_jitter_ms_zero_is_not_dropped():
    assert to_query(SlaProfileFilter(jitter_ms_min=0))["jitterMsMin"] == "0"


def test_owner_is_trimmed_into_query():
    assert to_query(SlaProfileFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(SlaProfileFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(SlaProfileCriticality)[:2]
    query = to_query(SlaProfileFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(SlaProfileFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(SlaProfileFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(SlaProfileFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


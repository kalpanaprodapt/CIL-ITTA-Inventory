"""Filter translation tests for Discovery Profile."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.discovery.discovery_profile.filters import DiscoveryProfileFilter, to_query
from itta_api.domains.discovery.discovery_profile.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(DiscoveryProfileFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(DiscoveryProfileFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(DiscoveryProfileFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(DiscoveryProfileFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(DiscoveryProfileFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(DiscoveryProfileFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(DiscoveryProfileFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(DiscoveryProfileFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(DiscoveryProfileFilter(external_ref=""))


def test_profile_name_is_trimmed_into_query():
    assert to_query(DiscoveryProfileFilter(profile_name="  abc ")) == {"profileName": "abc"}


def test_profile_name_blank_is_omitted():
    assert "profileName" not in to_query(DiscoveryProfileFilter(profile_name=""))


def test_protocol_values_are_comma_joined():
    members = list(DiscoveryProfileProtocol)[:2]
    query = to_query(DiscoveryProfileFilter(protocol=members))
    assert query["protocol"] == ",".join(m.value for m in members)


def test_credential_ref_is_trimmed_into_query():
    assert to_query(DiscoveryProfileFilter(credential_ref="  abc ")) == {"credentialRef": "abc"}


def test_credential_ref_blank_is_omitted():
    assert "credentialRef" not in to_query(DiscoveryProfileFilter(credential_ref=""))


def test_scope_cidr_is_trimmed_into_query():
    assert to_query(DiscoveryProfileFilter(scope_cidr="  abc ")) == {"scopeCidr": "abc"}


def test_scope_cidr_blank_is_omitted():
    assert "scopeCidr" not in to_query(DiscoveryProfileFilter(scope_cidr=""))


def test_schedule_cron_is_trimmed_into_query():
    assert to_query(DiscoveryProfileFilter(schedule_cron="  abc ")) == {"scheduleCron": "abc"}


def test_schedule_cron_blank_is_omitted():
    assert "scheduleCron" not in to_query(DiscoveryProfileFilter(schedule_cron=""))


def test_cost_center_is_trimmed_into_query():
    assert to_query(DiscoveryProfileFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(DiscoveryProfileFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(DiscoveryProfileFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(DiscoveryProfileFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(DiscoveryProfileFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(DiscoveryProfileFilter(labels=""))


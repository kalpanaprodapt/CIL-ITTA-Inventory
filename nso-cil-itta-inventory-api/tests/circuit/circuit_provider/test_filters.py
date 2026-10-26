"""Filter translation tests for Circuit Provider."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.circuit.circuit_provider.filters import CircuitProviderFilter, to_query
from itta_api.domains.circuit.circuit_provider.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(CircuitProviderFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(CircuitProviderFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(CircuitProviderFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(CircuitProviderFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(CircuitProviderFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(CircuitProviderFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(CircuitProviderFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(CircuitProviderFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(CircuitProviderFilter(external_ref=""))


def test_provider_name_is_trimmed_into_query():
    assert to_query(CircuitProviderFilter(provider_name="  abc ")) == {"providerName": "abc"}


def test_provider_name_blank_is_omitted():
    assert "providerName" not in to_query(CircuitProviderFilter(provider_name=""))


def test_account_number_is_trimmed_into_query():
    assert to_query(CircuitProviderFilter(account_number="  abc ")) == {"accountNumber": "abc"}


def test_account_number_blank_is_omitted():
    assert "accountNumber" not in to_query(CircuitProviderFilter(account_number=""))


def test_noc_is_trimmed_into_query():
    assert to_query(CircuitProviderFilter(noc="  abc ")) == {"noc": "abc"}


def test_noc_blank_is_omitted():
    assert "noc" not in to_query(CircuitProviderFilter(noc=""))


def test_escalation_email_is_trimmed_into_query():
    assert to_query(CircuitProviderFilter(escalation_email="  abc ")) == {"escalationEmail": "abc"}


def test_escalation_email_blank_is_omitted():
    assert "escalationEmail" not in to_query(CircuitProviderFilter(escalation_email=""))


def test_provider_type_values_are_comma_joined():
    members = list(CircuitProviderProviderType)[:2]
    query = to_query(CircuitProviderFilter(provider_type=members))
    assert query["providerType"] == ",".join(m.value for m in members)


def test_owner_is_trimmed_into_query():
    assert to_query(CircuitProviderFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(CircuitProviderFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(CircuitProviderCriticality)[:2]
    query = to_query(CircuitProviderFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(CircuitProviderFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(CircuitProviderFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(CircuitProviderFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


"""Filter translation tests for Ticket Link."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.alarm.ticket_link.filters import TicketLinkFilter, to_query
from itta_api.domains.alarm.ticket_link.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(TicketLinkFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(TicketLinkFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(TicketLinkFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(TicketLinkFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(TicketLinkFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(TicketLinkFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(TicketLinkFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(TicketLinkFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(TicketLinkFilter(external_ref=""))


def test_alarm_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(TicketLinkFilter(alarm_id=value))["alarmId"] == str(value)


def test_ticket_system_values_are_comma_joined():
    members = list(TicketLinkTicketSystem)[:2]
    query = to_query(TicketLinkFilter(ticket_system=members))
    assert query["ticketSystem"] == ",".join(m.value for m in members)


def test_ticket_number_is_trimmed_into_query():
    assert to_query(TicketLinkFilter(ticket_number="  abc ")) == {"ticketNumber": "abc"}


def test_ticket_number_blank_is_omitted():
    assert "ticketNumber" not in to_query(TicketLinkFilter(ticket_number=""))


def test_linked_at_window_is_iso_formatted():
    query = to_query(TicketLinkFilter(linked_at_from=datetime(2026, 1, 1, tzinfo=timezone.utc), linked_at_to=datetime(2026, 1, 1, tzinfo=timezone.utc)))
    assert query["linkedAtFrom"].startswith("2026-01-01")
    assert query["linkedAtTo"].startswith("2026-01-01")


def test_owner_is_trimmed_into_query():
    assert to_query(TicketLinkFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(TicketLinkFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(TicketLinkCriticality)[:2]
    query = to_query(TicketLinkFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(TicketLinkFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(TicketLinkFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(TicketLinkFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


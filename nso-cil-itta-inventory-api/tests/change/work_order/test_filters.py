"""Filter translation tests for Work Order."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.change.work_order.filters import WorkOrderFilter, to_query
from itta_api.domains.change.work_order.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(WorkOrderFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(WorkOrderFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(WorkOrderFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(WorkOrderFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(WorkOrderFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(WorkOrderFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(WorkOrderFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(WorkOrderFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(WorkOrderFilter(external_ref=""))


def test_work_order_number_is_trimmed_into_query():
    assert to_query(WorkOrderFilter(work_order_number="  abc ")) == {"workOrderNumber": "abc"}


def test_work_order_number_blank_is_omitted():
    assert "workOrderNumber" not in to_query(WorkOrderFilter(work_order_number=""))


def test_work_type_is_trimmed_into_query():
    assert to_query(WorkOrderFilter(work_type="  abc ")) == {"workType": "abc"}


def test_work_type_blank_is_omitted():
    assert "workType" not in to_query(WorkOrderFilter(work_type=""))


def test_assigned_technician_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(WorkOrderFilter(assigned_technician_id=value))["assignedTechnicianId"] == str(value)


def test_due_date_window_is_iso_formatted():
    query = to_query(WorkOrderFilter(due_date_from=date(2026, 1, 1), due_date_to=date(2026, 1, 1)))
    assert query["dueDateFrom"].startswith("2026-01-01")
    assert query["dueDateTo"].startswith("2026-01-01")


def test_work_state_values_are_comma_joined():
    members = list(WorkOrderWorkState)[:2]
    query = to_query(WorkOrderFilter(work_state=members))
    assert query["workState"] == ",".join(m.value for m in members)


def test_lifecycle_stage_values_are_comma_joined():
    members = list(WorkOrderLifecycleStage)[:2]
    query = to_query(WorkOrderFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(WorkOrderFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(WorkOrderFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(WorkOrderFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(WorkOrderFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(WorkOrderFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(WorkOrderFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(WorkOrderCriticality)[:2]
    query = to_query(WorkOrderFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


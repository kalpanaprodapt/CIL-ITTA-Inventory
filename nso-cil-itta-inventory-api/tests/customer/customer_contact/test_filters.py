"""Filter translation tests for Customer Contact."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.customer.customer_contact.filters import CustomerContactFilter, to_query
from itta_api.domains.customer.customer_contact.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(CustomerContactFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(CustomerContactFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(CustomerContactFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(CustomerContactFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(CustomerContactFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(CustomerContactFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(CustomerContactFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(CustomerContactFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(CustomerContactFilter(external_ref=""))


def test_customer_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(CustomerContactFilter(customer_id=value))["customerId"] == str(value)


def test_full_name_is_trimmed_into_query():
    assert to_query(CustomerContactFilter(full_name="  abc ")) == {"fullName": "abc"}


def test_full_name_blank_is_omitted():
    assert "fullName" not in to_query(CustomerContactFilter(full_name=""))


def test_email_is_trimmed_into_query():
    assert to_query(CustomerContactFilter(email="  abc ")) == {"email": "abc"}


def test_email_blank_is_omitted():
    assert "email" not in to_query(CustomerContactFilter(email=""))


def test_phone_is_trimmed_into_query():
    assert to_query(CustomerContactFilter(phone="  abc ")) == {"phone": "abc"}


def test_phone_blank_is_omitted():
    assert "phone" not in to_query(CustomerContactFilter(phone=""))


def test_contact_role_values_are_comma_joined():
    members = list(CustomerContactContactRole)[:2]
    query = to_query(CustomerContactFilter(contact_role=members))
    assert query["contactRole"] == ",".join(m.value for m in members)


def test_labels_is_trimmed_into_query():
    assert to_query(CustomerContactFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(CustomerContactFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(CustomerContactLifecycleStage)[:2]
    query = to_query(CustomerContactFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(CustomerContactFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(CustomerContactFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(CustomerContactFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(CustomerContactFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(CustomerContactFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(CustomerContactFilter(owner=""))


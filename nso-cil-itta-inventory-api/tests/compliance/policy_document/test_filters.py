"""Filter translation tests for Policy Document."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.compliance.policy_document.filters import PolicyDocumentFilter, to_query
from itta_api.domains.compliance.policy_document.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(PolicyDocumentFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(PolicyDocumentFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(PolicyDocumentFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(PolicyDocumentFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(PolicyDocumentFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(PolicyDocumentFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(PolicyDocumentFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(PolicyDocumentFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(PolicyDocumentFilter(external_ref=""))


def test_title_is_trimmed_into_query():
    assert to_query(PolicyDocumentFilter(title="  abc ")) == {"title": "abc"}


def test_title_blank_is_omitted():
    assert "title" not in to_query(PolicyDocumentFilter(title=""))


def test_doc_version_is_trimmed_into_query():
    assert to_query(PolicyDocumentFilter(doc_version="  abc ")) == {"docVersion": "abc"}


def test_doc_version_blank_is_omitted():
    assert "docVersion" not in to_query(PolicyDocumentFilter(doc_version=""))


def test_effective_date_window_is_iso_formatted():
    query = to_query(PolicyDocumentFilter(effective_date_from=date(2026, 1, 1), effective_date_to=date(2026, 1, 1)))
    assert query["effectiveDateFrom"].startswith("2026-01-01")
    assert query["effectiveDateTo"].startswith("2026-01-01")


def test_owner_is_trimmed_into_query():
    assert to_query(PolicyDocumentFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(PolicyDocumentFilter(owner=""))


def test_document_url_is_trimmed_into_query():
    assert to_query(PolicyDocumentFilter(document_url="  abc ")) == {"documentUrl": "abc"}


def test_document_url_blank_is_omitted():
    assert "documentUrl" not in to_query(PolicyDocumentFilter(document_url=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(PolicyDocumentLifecycleStage)[:2]
    query = to_query(PolicyDocumentFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(PolicyDocumentFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(PolicyDocumentFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(PolicyDocumentFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(PolicyDocumentFilter(region=""))


def test_criticality_values_are_comma_joined():
    members = list(PolicyDocumentCriticality)[:2]
    query = to_query(PolicyDocumentFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(PolicyDocumentFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(PolicyDocumentFilter(cost_center=""))


"""Filter translation tests for Service Dependency."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.service.service_dependency.filters import ServiceDependencyFilter, to_query
from itta_api.domains.service.service_dependency.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(ServiceDependencyFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(ServiceDependencyFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(ServiceDependencyFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(ServiceDependencyFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(ServiceDependencyFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(ServiceDependencyFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(ServiceDependencyFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(ServiceDependencyFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(ServiceDependencyFilter(external_ref=""))


def test_service_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(ServiceDependencyFilter(service_id=value))["serviceId"] == str(value)


def test_depends_on_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(ServiceDependencyFilter(depends_on_id=value))["dependsOnId"] == str(value)


def test_dependency_type_values_are_comma_joined():
    members = list(ServiceDependencyDependencyType)[:2]
    query = to_query(ServiceDependencyFilter(dependency_type=members))
    assert query["dependencyType"] == ",".join(m.value for m in members)


def test_impact_score_range_is_sent():
    query = to_query(ServiceDependencyFilter(impact_score_min=Decimal("1.5"), impact_score_max=Decimal("9.5")))
    assert query["impactScoreMin"] == str(Decimal("1.5"))
    assert query["impactScoreMax"] == str(Decimal("9.5"))


def test_impact_score_zero_is_not_dropped():
    assert to_query(ServiceDependencyFilter(impact_score_min=0))["impactScoreMin"] == "0"


def test_criticality_values_are_comma_joined():
    members = list(ServiceDependencyCriticality)[:2]
    query = to_query(ServiceDependencyFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(ServiceDependencyFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(ServiceDependencyFilter(cost_center=""))


def test_effective_from_window_is_iso_formatted():
    query = to_query(ServiceDependencyFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(ServiceDependencyFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


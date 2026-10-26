"""Filter translation tests for Integration Endpoint."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.integration.integration_endpoint.filters import IntegrationEndpointFilter, to_query
from itta_api.domains.integration.integration_endpoint.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(IntegrationEndpointFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(IntegrationEndpointFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(IntegrationEndpointFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(IntegrationEndpointFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(IntegrationEndpointFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(IntegrationEndpointFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(IntegrationEndpointFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(IntegrationEndpointFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(IntegrationEndpointFilter(external_ref=""))


def test_endpoint_name_is_trimmed_into_query():
    assert to_query(IntegrationEndpointFilter(endpoint_name="  abc ")) == {"endpointName": "abc"}


def test_endpoint_name_blank_is_omitted():
    assert "endpointName" not in to_query(IntegrationEndpointFilter(endpoint_name=""))


def test_target_system_is_trimmed_into_query():
    assert to_query(IntegrationEndpointFilter(target_system="  abc ")) == {"targetSystem": "abc"}


def test_target_system_blank_is_omitted():
    assert "targetSystem" not in to_query(IntegrationEndpointFilter(target_system=""))


def test_base_url_is_trimmed_into_query():
    assert to_query(IntegrationEndpointFilter(base_url="  abc ")) == {"baseUrl": "abc"}


def test_base_url_blank_is_omitted():
    assert "baseUrl" not in to_query(IntegrationEndpointFilter(base_url=""))


def test_auth_type_values_are_comma_joined():
    members = list(IntegrationEndpointAuthType)[:2]
    query = to_query(IntegrationEndpointFilter(auth_type=members))
    assert query["authType"] == ",".join(m.value for m in members)


def test_timeout_ms_range_is_sent():
    query = to_query(IntegrationEndpointFilter(timeout_ms_min=1, timeout_ms_max=9))
    assert query["timeoutMsMin"] == str(1)
    assert query["timeoutMsMax"] == str(9)


def test_timeout_ms_zero_is_not_dropped():
    assert to_query(IntegrationEndpointFilter(timeout_ms_min=0))["timeoutMsMin"] == "0"


def test_source_system_is_trimmed_into_query():
    assert to_query(IntegrationEndpointFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(IntegrationEndpointFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(IntegrationEndpointFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(IntegrationEndpointFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(IntegrationEndpointFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(IntegrationEndpointFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(IntegrationEndpointCriticality)[:2]
    query = to_query(IntegrationEndpointFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


def test_cost_center_is_trimmed_into_query():
    assert to_query(IntegrationEndpointFilter(cost_center="  abc ")) == {"costCenter": "abc"}


def test_cost_center_blank_is_omitted():
    assert "costCenter" not in to_query(IntegrationEndpointFilter(cost_center=""))


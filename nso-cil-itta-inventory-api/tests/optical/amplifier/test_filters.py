"""Filter translation tests for Amplifier."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.optical.amplifier.filters import AmplifierFilter, to_query
from itta_api.domains.optical.amplifier.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(AmplifierFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(AmplifierFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(AmplifierFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(AmplifierFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(AmplifierFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(AmplifierFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(AmplifierFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(AmplifierFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(AmplifierFilter(external_ref=""))


def test_system_id_is_stringified():
    value = UUID("00000000-0000-0000-0000-000000000011")
    assert to_query(AmplifierFilter(system_id=value))["systemId"] == str(value)


def test_amp_type_values_are_comma_joined():
    members = list(AmplifierAmpType)[:2]
    query = to_query(AmplifierFilter(amp_type=members))
    assert query["ampType"] == ",".join(m.value for m in members)


def test_gain_db_range_is_sent():
    query = to_query(AmplifierFilter(gain_db_min=Decimal("1.5"), gain_db_max=Decimal("9.5")))
    assert query["gainDbMin"] == str(Decimal("1.5"))
    assert query["gainDbMax"] == str(Decimal("9.5"))


def test_gain_db_zero_is_not_dropped():
    assert to_query(AmplifierFilter(gain_db_min=0))["gainDbMin"] == "0"


def test_output_power_dbm_range_is_sent():
    query = to_query(AmplifierFilter(output_power_dbm_min=Decimal("1.5"), output_power_dbm_max=Decimal("9.5")))
    assert query["outputPowerDbmMin"] == str(Decimal("1.5"))
    assert query["outputPowerDbmMax"] == str(Decimal("9.5"))


def test_output_power_dbm_zero_is_not_dropped():
    assert to_query(AmplifierFilter(output_power_dbm_min=0))["outputPowerDbmMin"] == "0"


def test_noise_figure_db_range_is_sent():
    query = to_query(AmplifierFilter(noise_figure_db_min=Decimal("1.5"), noise_figure_db_max=Decimal("9.5")))
    assert query["noiseFigureDbMin"] == str(Decimal("1.5"))
    assert query["noiseFigureDbMax"] == str(Decimal("9.5"))


def test_noise_figure_db_zero_is_not_dropped():
    assert to_query(AmplifierFilter(noise_figure_db_min=0))["noiseFigureDbMin"] == "0"


def test_lifecycle_stage_values_are_comma_joined():
    members = list(AmplifierLifecycleStage)[:2]
    query = to_query(AmplifierFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(AmplifierFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(AmplifierFilter(source_system=""))


def test_region_is_trimmed_into_query():
    assert to_query(AmplifierFilter(region="  abc ")) == {"region": "abc"}


def test_region_blank_is_omitted():
    assert "region" not in to_query(AmplifierFilter(region=""))


def test_owner_is_trimmed_into_query():
    assert to_query(AmplifierFilter(owner="  abc ")) == {"owner": "abc"}


def test_owner_blank_is_omitted():
    assert "owner" not in to_query(AmplifierFilter(owner=""))


def test_criticality_values_are_comma_joined():
    members = list(AmplifierCriticality)[:2]
    query = to_query(AmplifierFilter(criticality=members))
    assert query["criticality"] == ",".join(m.value for m in members)


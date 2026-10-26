"""Filter translation tests for Import Batch."""
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.integration.import_batch.filters import ImportBatchFilter, to_query
from itta_api.domains.integration.import_batch.schemas import *  # noqa: F401,F403


def test_empty_filter_has_no_params():
    assert to_query(ImportBatchFilter()) == {}


def test_free_text_is_trimmed():
    assert to_query(ImportBatchFilter(q="  core ")) == {"q": "core"}


def test_status_list_is_comma_joined():
    query = to_query(ImportBatchFilter(status=[LifecycleStatus.ACTIVE, LifecycleStatus.PLANNED]))
    assert query["status"] == "ACTIVE,PLANNED"


def test_code_is_trimmed_into_query():
    assert to_query(ImportBatchFilter(code="  abc ")) == {"code": "abc"}


def test_code_blank_is_omitted():
    assert "code" not in to_query(ImportBatchFilter(code=""))


def test_name_is_trimmed_into_query():
    assert to_query(ImportBatchFilter(name="  abc ")) == {"name": "abc"}


def test_name_blank_is_omitted():
    assert "name" not in to_query(ImportBatchFilter(name=""))


def test_external_ref_is_trimmed_into_query():
    assert to_query(ImportBatchFilter(external_ref="  abc ")) == {"externalRef": "abc"}


def test_external_ref_blank_is_omitted():
    assert "externalRef" not in to_query(ImportBatchFilter(external_ref=""))


def test_file_name_is_trimmed_into_query():
    assert to_query(ImportBatchFilter(file_name="  abc ")) == {"fileName": "abc"}


def test_file_name_blank_is_omitted():
    assert "fileName" not in to_query(ImportBatchFilter(file_name=""))


def test_entity_type_is_trimmed_into_query():
    assert to_query(ImportBatchFilter(entity_type="  abc ")) == {"entityType": "abc"}


def test_entity_type_blank_is_omitted():
    assert "entityType" not in to_query(ImportBatchFilter(entity_type=""))


def test_total_rows_range_is_sent():
    query = to_query(ImportBatchFilter(total_rows_min=1, total_rows_max=9))
    assert query["totalRowsMin"] == str(1)
    assert query["totalRowsMax"] == str(9)


def test_total_rows_zero_is_not_dropped():
    assert to_query(ImportBatchFilter(total_rows_min=0))["totalRowsMin"] == "0"


def test_processed_rows_range_is_sent():
    query = to_query(ImportBatchFilter(processed_rows_min=1, processed_rows_max=9))
    assert query["processedRowsMin"] == str(1)
    assert query["processedRowsMax"] == str(9)


def test_processed_rows_zero_is_not_dropped():
    assert to_query(ImportBatchFilter(processed_rows_min=0))["processedRowsMin"] == "0"


def test_failed_rows_range_is_sent():
    query = to_query(ImportBatchFilter(failed_rows_min=1, failed_rows_max=9))
    assert query["failedRowsMin"] == str(1)
    assert query["failedRowsMax"] == str(9)


def test_failed_rows_zero_is_not_dropped():
    assert to_query(ImportBatchFilter(failed_rows_min=0))["failedRowsMin"] == "0"


def test_batch_state_values_are_comma_joined():
    members = list(ImportBatchBatchState)[:2]
    query = to_query(ImportBatchFilter(batch_state=members))
    assert query["batchState"] == ",".join(m.value for m in members)


def test_effective_from_window_is_iso_formatted():
    query = to_query(ImportBatchFilter(effective_from_from=date(2026, 1, 1), effective_from_to=date(2026, 1, 1)))
    assert query["effectiveFromFrom"].startswith("2026-01-01")
    assert query["effectiveFromTo"].startswith("2026-01-01")


def test_effective_to_window_is_iso_formatted():
    query = to_query(ImportBatchFilter(effective_to_from=date(2026, 1, 1), effective_to_to=date(2026, 1, 1)))
    assert query["effectiveToFrom"].startswith("2026-01-01")
    assert query["effectiveToTo"].startswith("2026-01-01")


def test_labels_is_trimmed_into_query():
    assert to_query(ImportBatchFilter(labels="  abc ")) == {"labels": "abc"}


def test_labels_blank_is_omitted():
    assert "labels" not in to_query(ImportBatchFilter(labels=""))


def test_lifecycle_stage_values_are_comma_joined():
    members = list(ImportBatchLifecycleStage)[:2]
    query = to_query(ImportBatchFilter(lifecycle_stage=members))
    assert query["lifecycleStage"] == ",".join(m.value for m in members)


def test_source_system_is_trimmed_into_query():
    assert to_query(ImportBatchFilter(source_system="  abc ")) == {"sourceSystem": "abc"}


def test_source_system_blank_is_omitted():
    assert "sourceSystem" not in to_query(ImportBatchFilter(source_system=""))


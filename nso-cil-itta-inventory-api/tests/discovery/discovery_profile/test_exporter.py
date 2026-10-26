"""Exporter tests for Discovery Profile."""
import csv
import io
import json
from datetime import date, datetime, timezone
from decimal import Decimal
from uuid import UUID

from itta_core.models import LifecycleStatus
from itta_api.domains.discovery.discovery_profile.exporter import COLUMNS, header_labels, to_csv, to_json_lines, to_row
from itta_api.domains.discovery.discovery_profile.schemas import *  # noqa: F401,F403
from tests.discovery.discovery_profile.factories import read_model


def test_columns_cover_every_field():
    assert len(COLUMNS) == 15
    assert len(header_labels()) == len(COLUMNS)


def test_csv_has_header_and_one_row_per_item():
    text = to_csv([read_model(), read_model(code="OTHER-1")])
    rows = list(csv.DictReader(io.StringIO(text)))
    assert len(rows) == 2
    assert rows[1]["code"] == "OTHER-1"


def test_csv_of_nothing_is_header_only():
    assert len(to_csv([]).strip().splitlines()) == 1


def test_json_lines_are_valid_json():
    lines = to_json_lines([read_model(), read_model()]).splitlines()
    assert all(json.loads(line)["code"] == "DISCOV-0001" for line in lines)


def test_status_is_rendered_as_value():
    assert to_row(read_model())["status"] == "ACTIVE"


def test_none_renders_as_empty_string():
    assert to_row(read_model(description=None))["description"] == ""


def test_row_has_every_field():
    assert set(to_row(read_model())) == {"code", "name", "description", "status", "external_ref", "profile_name", "protocol", "credential_ref", "scope_cidr", "schedule_cron", "cost_center", "notes", "effective_from", "effective_to", "labels"}

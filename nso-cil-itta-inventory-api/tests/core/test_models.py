import pytest
from pydantic import ValidationError

from itta_core.models import ApiModel, LifecycleStatus, reject_control_chars
from itta_core.pagination import Page


class Sample(ApiModel):
    first_name: str


def test_camel_case_alias_and_population_by_name():
    assert Sample(firstName="a").first_name == "a"
    assert Sample(first_name="a").model_dump(by_alias=True) == {"firstName": "a"}


def test_extra_fields_forbidden():
    with pytest.raises(ValidationError):
        Sample(first_name="a", other=1)


def test_control_characters_rejected():
    assert reject_control_chars("ok", "f") == "ok"
    with pytest.raises(ValueError):
        reject_control_chars("bad\x07", "f")


def test_page_has_next():
    page = Page[int](content=[1], page=0, size=1, total_elements=2, total_pages=2)
    assert page.has_next
    assert not Page[int](content=[], page=1, size=1, total_elements=2, total_pages=2).has_next


def test_lifecycle_values():
    assert [s.value for s in LifecycleStatus] == ["PLANNED", "ACTIVE", "MAINTENANCE", "DECOMMISSIONED"]

"""Generic page envelope matching the Spring PageResponse."""
from __future__ import annotations

from typing import Generic, TypeVar

from pydantic import Field

from itta_core.models import ApiModel

T = TypeVar("T")


class Page(ApiModel, Generic[T]):
    content: list[T] = Field(default_factory=list)
    page: int = 0
    size: int = 20
    total_elements: int = 0
    total_pages: int = 0

    @property
    def has_next(self) -> bool:
        return self.page + 1 < self.total_pages

"""Field catalogue for Pseudo Wire; drives the UI forms, tables and filters."""
from itta_core.models import EntityMeta, FieldMeta

ENTITY = "PseudoWire"
PATH = "pseudo-wires"

FIELDS: tuple[FieldMeta, ...] = (
    FieldMeta(
        name="code",
        label="Code",
        type="string",
        required=True,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="name",
        label="Name",
        type="string",
        required=True,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="description",
        label="Description",
        type="text",
        required=False,
        max_length=4000,
        enum_values=(),
        filterable=False,
        sortable=False,
    ),
    FieldMeta(
        name="status",
        label="Status",
        type="enum",
        required=True,
        max_length=None,
        enum_values=("PLANNED", "ACTIVE", "MAINTENANCE", "DECOMMISSIONED",),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="externalRef",
        label="External Ref",
        type="string",
        required=False,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="vcId",
        label="Vc Id",
        type="integer",
        required=True,
        max_length=None,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="aEndId",
        label="A End Id",
        type="reference",
        required=True,
        max_length=None,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="zEndId",
        label="Z End Id",
        type="reference",
        required=True,
        max_length=None,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="encapsulation",
        label="Encapsulation",
        type="string",
        required=False,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="controlWord",
        label="Control Word",
        type="boolean",
        required=False,
        max_length=None,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="costCenter",
        label="Cost Center",
        type="string",
        required=False,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="notes",
        label="Notes",
        type="text",
        required=False,
        max_length=4000,
        enum_values=(),
        filterable=False,
        sortable=False,
    ),
    FieldMeta(
        name="effectiveFrom",
        label="Effective From",
        type="date",
        required=False,
        max_length=None,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="effectiveTo",
        label="Effective To",
        type="date",
        required=False,
        max_length=None,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="labels",
        label="Labels",
        type="string",
        required=False,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
)

META = EntityMeta(
    entity=ENTITY,
    title="Pseudo Wire",
    domain="mpls",
    path=PATH,
    fields=FIELDS,
)


def field_names() -> list[str]:
    return [f.name for f in FIELDS]


def required_fields() -> list[str]:
    return [f.name for f in FIELDS if f.required]

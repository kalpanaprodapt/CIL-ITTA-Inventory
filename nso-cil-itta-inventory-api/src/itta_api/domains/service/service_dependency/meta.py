"""Field catalogue for Service Dependency; drives the UI forms, tables and filters."""
from itta_core.models import EntityMeta, FieldMeta

ENTITY = "ServiceDependency"
PATH = "service-dependencies"

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
        name="serviceId",
        label="Service Id",
        type="reference",
        required=True,
        max_length=None,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="dependsOnId",
        label="Depends On Id",
        type="reference",
        required=True,
        max_length=None,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="dependencyType",
        label="Dependency Type",
        type="enum",
        required=False,
        max_length=None,
        enum_values=("HARD", "SOFT", "OPTIONAL",),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="impactScore",
        label="Impact Score",
        type="decimal",
        required=False,
        max_length=None,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="criticality",
        label="Criticality",
        type="enum",
        required=False,
        max_length=None,
        enum_values=("LOW", "MEDIUM", "HIGH", "CRITICAL",),
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
)

META = EntityMeta(
    entity=ENTITY,
    title="Service Dependency",
    domain="service",
    path=PATH,
    fields=FIELDS,
)


def field_names() -> list[str]:
    return [f.name for f in FIELDS]


def required_fields() -> list[str]:
    return [f.name for f in FIELDS if f.required]

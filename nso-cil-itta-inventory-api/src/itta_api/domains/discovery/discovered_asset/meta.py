"""Field catalogue for Discovered Asset; drives the UI forms, tables and filters."""
from itta_core.models import EntityMeta, FieldMeta

ENTITY = "DiscoveredAsset"
PATH = "discovered-assets"

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
        name="jobId",
        label="Job Id",
        type="reference",
        required=True,
        max_length=None,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="assetType",
        label="Asset Type",
        type="string",
        required=True,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="fingerprint",
        label="Fingerprint",
        type="string",
        required=True,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="rawPayload",
        label="Raw Payload",
        type="text",
        required=False,
        max_length=4000,
        enum_values=(),
        filterable=False,
        sortable=False,
    ),
    FieldMeta(
        name="matchedAssetId",
        label="Matched Asset Id",
        type="reference",
        required=False,
        max_length=None,
        enum_values=(),
        filterable=True,
        sortable=True,
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
    FieldMeta(
        name="lifecycleStage",
        label="Lifecycle Stage",
        type="enum",
        required=False,
        max_length=None,
        enum_values=("PLAN", "BUILD", "OPERATE", "RETIRE",),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="sourceSystem",
        label="Source System",
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
    title="Discovered Asset",
    domain="discovery",
    path=PATH,
    fields=FIELDS,
)


def field_names() -> list[str]:
    return [f.name for f in FIELDS]


def required_fields() -> list[str]:
    return [f.name for f in FIELDS if f.required]

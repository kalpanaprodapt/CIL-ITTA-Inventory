import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { ASSET_TAG_TAG_TYPE_OPTIONS, ASSET_TAG_CRITICALITY_OPTIONS } from './types';
import type { AssetTagFilter } from './types';

interface Props {
  value: AssetTagFilter;
  onChange: (next: AssetTagFilter) => void;
  onReset: () => void;
}

export function AssetTagFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: AssetTagFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Asset Tag filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Asset Tag"
          placeholder="code, name or description"
          value={value.q ?? ''}
          onChange={(v) => patch({ q: v || undefined })}
        />
      </FilterField>
      <FilterField label="Status">
        <MultiSelect
          ariaLabel="Status filter"
          options={LIFECYCLE_OPTIONS}
          value={value.status ?? []}
          onChange={(v) => patch({ status: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Code">
        <Input
          aria-label="Code filter"
          value={value.code ?? ''}
          onChange={(v) => patch({ code: v || undefined })}
        />
      </FilterField>
      <FilterField label="Name">
        <Input
          aria-label="Name filter"
          value={value.name ?? ''}
          onChange={(v) => patch({ name: v || undefined })}
        />
      </FilterField>
      <FilterField label="External Ref">
        <Input
          aria-label="External Ref filter"
          value={value.externalRef ?? ''}
          onChange={(v) => patch({ externalRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Asset Id">
        <Input
          aria-label="Asset Id filter"
          placeholder="UUID"
          value={value.assetId ?? ''}
          onChange={(v) => patch({ assetId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Tag Value">
        <Input
          aria-label="Tag Value filter"
          value={value.tagValue ?? ''}
          onChange={(v) => patch({ tagValue: v || undefined })}
        />
      </FilterField>
      <FilterField label="Tag Type">
        <MultiSelect
          ariaLabel="Tag Type filter"
          options={ASSET_TAG_TAG_TYPE_OPTIONS}
          value={value.tagType ?? []}
          onChange={(v) => patch({ tagType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Printed On">
        <DateRange
          ariaLabel="Printed On"
          from={value.printedOnFrom}
          to={value.printedOnTo}
          onChange={(from, to) => patch({ printedOnFrom: from, printedOnTo: to })}
        />
      </FilterField>
      <FilterField label="Source System">
        <Input
          aria-label="Source System filter"
          value={value.sourceSystem ?? ''}
          onChange={(v) => patch({ sourceSystem: v || undefined })}
        />
      </FilterField>
      <FilterField label="Region">
        <Input
          aria-label="Region filter"
          value={value.region ?? ''}
          onChange={(v) => patch({ region: v || undefined })}
        />
      </FilterField>
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={ASSET_TAG_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Cost Center">
        <Input
          aria-label="Cost Center filter"
          value={value.costCenter ?? ''}
          onChange={(v) => patch({ costCenter: v || undefined })}
        />
      </FilterField>
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}

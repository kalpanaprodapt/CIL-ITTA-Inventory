import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { BUILDING_OWNERSHIP_OPTIONS, BUILDING_LIFECYCLE_STAGE_OPTIONS, BUILDING_CRITICALITY_OPTIONS } from './types';
import type { BuildingFilter } from './types';

interface Props {
  value: BuildingFilter;
  onChange: (next: BuildingFilter) => void;
  onReset: () => void;
}

export function BuildingFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: BuildingFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Building filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Building"
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
      <FilterField label="Site Id">
        <Input
          aria-label="Site Id filter"
          placeholder="UUID"
          value={value.siteId ?? ''}
          onChange={(v) => patch({ siteId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Floors">
        <RangeInputs
          ariaLabel="Floors"
          min={value.floorsMin}
          max={value.floorsMax}
          onChange={(min, max) => patch({ floorsMin: min, floorsMax: max })}
        />
      </FilterField>
      <FilterField label="Year Built">
        <RangeInputs
          ariaLabel="Year Built"
          min={value.yearBuiltMin}
          max={value.yearBuiltMax}
          onChange={(min, max) => patch({ yearBuiltMin: min, yearBuiltMax: max })}
        />
      </FilterField>
      <FilterField label="Ownership">
        <MultiSelect
          ariaLabel="Ownership filter"
          options={BUILDING_OWNERSHIP_OPTIONS}
          value={value.ownership ?? []}
          onChange={(v) => patch({ ownership: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Lease Expiry">
        <DateRange
          ariaLabel="Lease Expiry"
          from={value.leaseExpiryFrom}
          to={value.leaseExpiryTo}
          onChange={(from, to) => patch({ leaseExpiryFrom: from, leaseExpiryTo: to })}
        />
      </FilterField>
      <FilterField label="Security Level">
        <RangeInputs
          ariaLabel="Security Level"
          min={value.securityLevelMin}
          max={value.securityLevelMax}
          onChange={(min, max) => patch({ securityLevelMin: min, securityLevelMax: max })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={BUILDING_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
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
          options={BUILDING_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
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

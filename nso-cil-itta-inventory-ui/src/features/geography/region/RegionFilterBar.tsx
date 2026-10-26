import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { REGION_REGION_TYPE_OPTIONS, REGION_CRITICALITY_OPTIONS } from './types';
import type { RegionFilter } from './types';

interface Props {
  value: RegionFilter;
  onChange: (next: RegionFilter) => void;
  onReset: () => void;
}

export function RegionFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: RegionFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Region filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Region"
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
      <FilterField label="Country Id">
        <Input
          aria-label="Country Id filter"
          placeholder="UUID"
          value={value.countryId ?? ''}
          onChange={(v) => patch({ countryId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Region Type">
        <MultiSelect
          ariaLabel="Region Type filter"
          options={REGION_REGION_TYPE_OPTIONS}
          value={value.regionType ?? []}
          onChange={(v) => patch({ regionType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Population">
        <RangeInputs
          ariaLabel="Population"
          min={value.populationMin}
          max={value.populationMax}
          onChange={(min, max) => patch({ populationMin: min, populationMax: max })}
        />
      </FilterField>
      <FilterField label="Latitude">
        <RangeInputs
          ariaLabel="Latitude"
          min={value.latitudeMin}
          max={value.latitudeMax}
          onChange={(min, max) => patch({ latitudeMin: min, latitudeMax: max })}
        />
      </FilterField>
      <FilterField label="Longitude">
        <RangeInputs
          ariaLabel="Longitude"
          min={value.longitudeMin}
          max={value.longitudeMax}
          onChange={(min, max) => patch({ longitudeMin: min, longitudeMax: max })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={REGION_CRITICALITY_OPTIONS}
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
      <FilterField label="Effective From">
        <DateRange
          ariaLabel="Effective From"
          from={value.effectiveFromFrom}
          to={value.effectiveFromTo}
          onChange={(from, to) => patch({ effectiveFromFrom: from, effectiveFromTo: to })}
        />
      </FilterField>
      <FilterField label="Effective To">
        <DateRange
          ariaLabel="Effective To"
          from={value.effectiveToFrom}
          to={value.effectiveToTo}
          onChange={(from, to) => patch({ effectiveToFrom: from, effectiveToTo: to })}
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

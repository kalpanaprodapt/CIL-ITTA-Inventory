import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { CITY_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { CityFilter } from './types';

interface Props {
  value: CityFilter;
  onChange: (next: CityFilter) => void;
  onReset: () => void;
}

export function CityFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: CityFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="City filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search City"
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
      <FilterField label="Region Id">
        <Input
          aria-label="Region Id filter"
          placeholder="UUID"
          value={value.regionId ?? ''}
          onChange={(v) => patch({ regionId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Postal Prefix">
        <Input
          aria-label="Postal Prefix filter"
          value={value.postalPrefix ?? ''}
          onChange={(v) => patch({ postalPrefix: v || undefined })}
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
      <FilterField label="Metro Area">
        <Input
          aria-label="Metro Area filter"
          value={value.metroArea ?? ''}
          onChange={(v) => patch({ metroArea: v || undefined })}
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
      <FilterField label="Labels">
        <Input
          aria-label="Labels filter"
          value={value.labels ?? ''}
          onChange={(v) => patch({ labels: v || undefined })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={CITY_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
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

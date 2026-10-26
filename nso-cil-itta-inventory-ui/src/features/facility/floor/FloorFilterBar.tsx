import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';

import type { FloorFilter } from './types';

interface Props {
  value: FloorFilter;
  onChange: (next: FloorFilter) => void;
  onReset: () => void;
}

export function FloorFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: FloorFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Floor filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Floor"
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
      <FilterField label="Building Id">
        <Input
          aria-label="Building Id filter"
          placeholder="UUID"
          value={value.buildingId ?? ''}
          onChange={(v) => patch({ buildingId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Level">
        <RangeInputs
          ariaLabel="Level"
          min={value.levelMin}
          max={value.levelMax}
          onChange={(min, max) => patch({ levelMin: min, levelMax: max })}
        />
      </FilterField>
      <FilterField label="Area Sqm">
        <RangeInputs
          ariaLabel="Area Sqm"
          min={value.areaSqmMin}
          max={value.areaSqmMax}
          onChange={(min, max) => patch({ areaSqmMin: min, areaSqmMax: max })}
        />
      </FilterField>
      <FilterField label="Load Capacity Kg">
        <RangeInputs
          ariaLabel="Load Capacity Kg"
          min={value.loadCapacityKgMin}
          max={value.loadCapacityKgMax}
          onChange={(min, max) => patch({ loadCapacityKgMin: min, loadCapacityKgMax: max })}
        />
      </FilterField>
      <FilterField label="Has Raised Floor">
        <TriState
          ariaLabel="Has Raised Floor filter"
          value={value.hasRaisedFloor}
          onChange={(v) => patch({ hasRaisedFloor: v })}
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
      <FilterField label="Labels">
        <Input
          aria-label="Labels filter"
          value={value.labels ?? ''}
          onChange={(v) => patch({ labels: v || undefined })}
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

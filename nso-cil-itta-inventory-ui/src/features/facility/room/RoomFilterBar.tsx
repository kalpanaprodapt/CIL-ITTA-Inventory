import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { ROOM_ROOM_TYPE_OPTIONS, ROOM_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { RoomFilter } from './types';

interface Props {
  value: RoomFilter;
  onChange: (next: RoomFilter) => void;
  onReset: () => void;
}

export function RoomFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: RoomFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Room filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Room"
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
      <FilterField label="Floor Id">
        <Input
          aria-label="Floor Id filter"
          placeholder="UUID"
          value={value.floorId ?? ''}
          onChange={(v) => patch({ floorId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Room Type">
        <MultiSelect
          ariaLabel="Room Type filter"
          options={ROOM_ROOM_TYPE_OPTIONS}
          value={value.roomType ?? []}
          onChange={(v) => patch({ roomType: v.length ? v : undefined })}
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
      <FilterField label="Cooling Tons">
        <RangeInputs
          ariaLabel="Cooling Tons"
          min={value.coolingTonsMin}
          max={value.coolingTonsMax}
          onChange={(min, max) => patch({ coolingTonsMin: min, coolingTonsMax: max })}
        />
      </FilterField>
      <FilterField label="Fire Zone">
        <Input
          aria-label="Fire Zone filter"
          value={value.fireZone ?? ''}
          onChange={(v) => patch({ fireZone: v || undefined })}
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
          options={ROOM_LIFECYCLE_STAGE_OPTIONS}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}

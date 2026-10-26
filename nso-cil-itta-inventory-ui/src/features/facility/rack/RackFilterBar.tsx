import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { RACK_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { RackFilter } from './types';

interface Props {
  value: RackFilter;
  onChange: (next: RackFilter) => void;
  onReset: () => void;
}

export function RackFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: RackFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Rack filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Rack"
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
      <FilterField label="Room Id">
        <Input
          aria-label="Room Id filter"
          placeholder="UUID"
          value={value.roomId ?? ''}
          onChange={(v) => patch({ roomId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Rack Units">
        <RangeInputs
          ariaLabel="Rack Units"
          min={value.rackUnitsMin}
          max={value.rackUnitsMax}
          onChange={(min, max) => patch({ rackUnitsMin: min, rackUnitsMax: max })}
        />
      </FilterField>
      <FilterField label="Width Mm">
        <RangeInputs
          ariaLabel="Width Mm"
          min={value.widthMmMin}
          max={value.widthMmMax}
          onChange={(min, max) => patch({ widthMmMin: min, widthMmMax: max })}
        />
      </FilterField>
      <FilterField label="Depth Mm">
        <RangeInputs
          ariaLabel="Depth Mm"
          min={value.depthMmMin}
          max={value.depthMmMax}
          onChange={(min, max) => patch({ depthMmMin: min, depthMmMax: max })}
        />
      </FilterField>
      <FilterField label="Max Weight Kg">
        <RangeInputs
          ariaLabel="Max Weight Kg"
          min={value.maxWeightKgMin}
          max={value.maxWeightKgMax}
          onChange={(min, max) => patch({ maxWeightKgMin: min, maxWeightKgMax: max })}
        />
      </FilterField>
      <FilterField label="Row Label">
        <Input
          aria-label="Row Label filter"
          value={value.rowLabel ?? ''}
          onChange={(v) => patch({ rowLabel: v || undefined })}
        />
      </FilterField>
      <FilterField label="Position">
        <Input
          aria-label="Position filter"
          value={value.position ?? ''}
          onChange={(v) => patch({ position: v || undefined })}
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
          options={RACK_LIFECYCLE_STAGE_OPTIONS}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}

import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { VLAN_POOL_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { VlanPoolFilter } from './types';

interface Props {
  value: VlanPoolFilter;
  onChange: (next: VlanPoolFilter) => void;
  onReset: () => void;
}

export function VlanPoolFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: VlanPoolFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Vlan Pool filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Vlan Pool"
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
      <FilterField label="Pool Name">
        <Input
          aria-label="Pool Name filter"
          value={value.poolName ?? ''}
          onChange={(v) => patch({ poolName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Range Start">
        <RangeInputs
          ariaLabel="Range Start"
          min={value.rangeStartMin}
          max={value.rangeStartMax}
          onChange={(min, max) => patch({ rangeStartMin: min, rangeStartMax: max })}
        />
      </FilterField>
      <FilterField label="Range End">
        <RangeInputs
          ariaLabel="Range End"
          min={value.rangeEndMin}
          max={value.rangeEndMax}
          onChange={(min, max) => patch({ rangeEndMin: min, rangeEndMax: max })}
        />
      </FilterField>
      <FilterField label="Allocated">
        <RangeInputs
          ariaLabel="Allocated"
          min={value.allocatedMin}
          max={value.allocatedMax}
          onChange={(min, max) => patch({ allocatedMin: min, allocatedMax: max })}
        />
      </FilterField>
      <FilterField label="Scope">
        <Input
          aria-label="Scope filter"
          value={value.scope ?? ''}
          onChange={(v) => patch({ scope: v || undefined })}
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
          options={VLAN_POOL_LIFECYCLE_STAGE_OPTIONS}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}

import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { LAG_GROUP_LACP_MODE_OPTIONS, LAG_GROUP_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { LagGroupFilter } from './types';

interface Props {
  value: LagGroupFilter;
  onChange: (next: LagGroupFilter) => void;
  onReset: () => void;
}

export function LagGroupFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: LagGroupFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Lag Group filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Lag Group"
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
      <FilterField label="Device Id">
        <Input
          aria-label="Device Id filter"
          placeholder="UUID"
          value={value.deviceId ?? ''}
          onChange={(v) => patch({ deviceId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Lag Name">
        <Input
          aria-label="Lag Name filter"
          value={value.lagName ?? ''}
          onChange={(v) => patch({ lagName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Member Count">
        <RangeInputs
          ariaLabel="Member Count"
          min={value.memberCountMin}
          max={value.memberCountMax}
          onChange={(min, max) => patch({ memberCountMin: min, memberCountMax: max })}
        />
      </FilterField>
      <FilterField label="Lacp Mode">
        <MultiSelect
          ariaLabel="Lacp Mode filter"
          options={LAG_GROUP_LACP_MODE_OPTIONS}
          value={value.lacpMode ?? []}
          onChange={(v) => patch({ lacpMode: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Min Links">
        <RangeInputs
          ariaLabel="Min Links"
          min={value.minLinksMin}
          max={value.minLinksMax}
          onChange={(min, max) => patch({ minLinksMin: min, minLinksMax: max })}
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
          options={LAG_GROUP_LIFECYCLE_STAGE_OPTIONS}
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

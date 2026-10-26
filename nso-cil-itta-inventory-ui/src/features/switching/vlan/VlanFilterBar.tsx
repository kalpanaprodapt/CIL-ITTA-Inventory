import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { VLAN_VLAN_TYPE_OPTIONS, VLAN_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { VlanFilter } from './types';

interface Props {
  value: VlanFilter;
  onChange: (next: VlanFilter) => void;
  onReset: () => void;
}

export function VlanFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: VlanFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Vlan filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Vlan"
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
      <FilterField label="Vlan Id">
        <RangeInputs
          ariaLabel="Vlan Id"
          min={value.vlanIdMin}
          max={value.vlanIdMax}
          onChange={(min, max) => patch({ vlanIdMin: min, vlanIdMax: max })}
        />
      </FilterField>
      <FilterField label="Vlan Name">
        <Input
          aria-label="Vlan Name filter"
          value={value.vlanName ?? ''}
          onChange={(v) => patch({ vlanName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Vlan Type">
        <MultiSelect
          ariaLabel="Vlan Type filter"
          options={VLAN_VLAN_TYPE_OPTIONS}
          value={value.vlanType ?? []}
          onChange={(v) => patch({ vlanType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Pool Id">
        <Input
          aria-label="Pool Id filter"
          placeholder="UUID"
          value={value.poolId ?? ''}
          onChange={(v) => patch({ poolId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Stp Mode">
        <Input
          aria-label="Stp Mode filter"
          value={value.stpMode ?? ''}
          onChange={(v) => patch({ stpMode: v || undefined })}
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
          options={VLAN_LIFECYCLE_STAGE_OPTIONS}
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

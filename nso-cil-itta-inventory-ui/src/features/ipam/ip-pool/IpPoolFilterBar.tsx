import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { IP_POOL_POOL_TYPE_OPTIONS, IP_POOL_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { IpPoolFilter } from './types';

interface Props {
  value: IpPoolFilter;
  onChange: (next: IpPoolFilter) => void;
  onReset: () => void;
}

export function IpPoolFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: IpPoolFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Ip Pool filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Ip Pool"
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
      <FilterField label="Subnet Id">
        <Input
          aria-label="Subnet Id filter"
          placeholder="UUID"
          value={value.subnetId ?? ''}
          onChange={(v) => patch({ subnetId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Range Start">
        <Input
          aria-label="Range Start filter"
          value={value.rangeStart ?? ''}
          onChange={(v) => patch({ rangeStart: v || undefined })}
        />
      </FilterField>
      <FilterField label="Range End">
        <Input
          aria-label="Range End filter"
          value={value.rangeEnd ?? ''}
          onChange={(v) => patch({ rangeEnd: v || undefined })}
        />
      </FilterField>
      <FilterField label="Pool Type">
        <MultiSelect
          ariaLabel="Pool Type filter"
          options={IP_POOL_POOL_TYPE_OPTIONS}
          value={value.poolType ?? []}
          onChange={(v) => patch({ poolType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Lease Seconds">
        <RangeInputs
          ariaLabel="Lease Seconds"
          min={value.leaseSecondsMin}
          max={value.leaseSecondsMax}
          onChange={(min, max) => patch({ leaseSecondsMin: min, leaseSecondsMax: max })}
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
          options={IP_POOL_LIFECYCLE_STAGE_OPTIONS}
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

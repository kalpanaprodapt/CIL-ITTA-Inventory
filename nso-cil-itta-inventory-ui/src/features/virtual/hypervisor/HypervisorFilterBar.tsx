import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { HYPERVISOR_HYPERVISOR_TYPE_OPTIONS, HYPERVISOR_CRITICALITY_OPTIONS } from './types';
import type { HypervisorFilter } from './types';

interface Props {
  value: HypervisorFilter;
  onChange: (next: HypervisorFilter) => void;
  onReset: () => void;
}

export function HypervisorFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: HypervisorFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Hypervisor filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Hypervisor"
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
      <FilterField label="Host Device Id">
        <Input
          aria-label="Host Device Id filter"
          placeholder="UUID"
          value={value.hostDeviceId ?? ''}
          onChange={(v) => patch({ hostDeviceId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Hypervisor Type">
        <MultiSelect
          ariaLabel="Hypervisor Type filter"
          options={HYPERVISOR_HYPERVISOR_TYPE_OPTIONS}
          value={value.hypervisorType ?? []}
          onChange={(v) => patch({ hypervisorType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Hv Version">
        <Input
          aria-label="Hv Version filter"
          value={value.hvVersion ?? ''}
          onChange={(v) => patch({ hvVersion: v || undefined })}
        />
      </FilterField>
      <FilterField label="Vcpu Capacity">
        <RangeInputs
          ariaLabel="Vcpu Capacity"
          min={value.vcpuCapacityMin}
          max={value.vcpuCapacityMax}
          onChange={(min, max) => patch({ vcpuCapacityMin: min, vcpuCapacityMax: max })}
        />
      </FilterField>
      <FilterField label="Memory Gb Capacity">
        <RangeInputs
          ariaLabel="Memory Gb Capacity"
          min={value.memoryGbCapacityMin}
          max={value.memoryGbCapacityMax}
          onChange={(min, max) => patch({ memoryGbCapacityMin: min, memoryGbCapacityMax: max })}
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
          options={HYPERVISOR_CRITICALITY_OPTIONS}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}

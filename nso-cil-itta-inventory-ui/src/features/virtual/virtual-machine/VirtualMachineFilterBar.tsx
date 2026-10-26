import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { VIRTUAL_MACHINE_POWER_STATE_OPTIONS, VIRTUAL_MACHINE_CRITICALITY_OPTIONS } from './types';
import type { VirtualMachineFilter } from './types';

interface Props {
  value: VirtualMachineFilter;
  onChange: (next: VirtualMachineFilter) => void;
  onReset: () => void;
}

export function VirtualMachineFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: VirtualMachineFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Virtual Machine filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Virtual Machine"
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
      <FilterField label="Hypervisor Id">
        <Input
          aria-label="Hypervisor Id filter"
          placeholder="UUID"
          value={value.hypervisorId ?? ''}
          onChange={(v) => patch({ hypervisorId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Vm Name">
        <Input
          aria-label="Vm Name filter"
          value={value.vmName ?? ''}
          onChange={(v) => patch({ vmName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Vcpu">
        <RangeInputs
          ariaLabel="Vcpu"
          min={value.vcpuMin}
          max={value.vcpuMax}
          onChange={(min, max) => patch({ vcpuMin: min, vcpuMax: max })}
        />
      </FilterField>
      <FilterField label="Memory Gb">
        <RangeInputs
          ariaLabel="Memory Gb"
          min={value.memoryGbMin}
          max={value.memoryGbMax}
          onChange={(min, max) => patch({ memoryGbMin: min, memoryGbMax: max })}
        />
      </FilterField>
      <FilterField label="Disk Gb">
        <RangeInputs
          ariaLabel="Disk Gb"
          min={value.diskGbMin}
          max={value.diskGbMax}
          onChange={(min, max) => patch({ diskGbMin: min, diskGbMax: max })}
        />
      </FilterField>
      <FilterField label="Power State">
        <MultiSelect
          ariaLabel="Power State filter"
          options={VIRTUAL_MACHINE_POWER_STATE_OPTIONS}
          value={value.powerState ?? []}
          onChange={(v) => patch({ powerState: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={VIRTUAL_MACHINE_CRITICALITY_OPTIONS}
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

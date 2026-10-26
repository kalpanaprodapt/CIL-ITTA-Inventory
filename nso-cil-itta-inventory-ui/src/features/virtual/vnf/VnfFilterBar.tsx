import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { VNF_LIFECYCLE_STAGE_OPTIONS, VNF_CRITICALITY_OPTIONS } from './types';
import type { VnfFilter } from './types';

interface Props {
  value: VnfFilter;
  onChange: (next: VnfFilter) => void;
  onReset: () => void;
}

export function VnfFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: VnfFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Vnf filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Vnf"
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
      <FilterField label="Vnf Name">
        <Input
          aria-label="Vnf Name filter"
          value={value.vnfName ?? ''}
          onChange={(v) => patch({ vnfName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Vnf Type">
        <Input
          aria-label="Vnf Type filter"
          value={value.vnfType ?? ''}
          onChange={(v) => patch({ vnfType: v || undefined })}
        />
      </FilterField>
      <FilterField label="Flavor">
        <Input
          aria-label="Flavor filter"
          value={value.flavor ?? ''}
          onChange={(v) => patch({ flavor: v || undefined })}
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
      <FilterField label="Instance Count">
        <RangeInputs
          ariaLabel="Instance Count"
          min={value.instanceCountMin}
          max={value.instanceCountMax}
          onChange={(min, max) => patch({ instanceCountMin: min, instanceCountMax: max })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={VNF_LIFECYCLE_STAGE_OPTIONS}
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
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={VNF_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
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

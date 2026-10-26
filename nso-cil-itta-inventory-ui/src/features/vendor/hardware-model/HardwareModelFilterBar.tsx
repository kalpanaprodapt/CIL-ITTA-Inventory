import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { HARDWARE_MODEL_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { HardwareModelFilter } from './types';

interface Props {
  value: HardwareModelFilter;
  onChange: (next: HardwareModelFilter) => void;
  onReset: () => void;
}

export function HardwareModelFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: HardwareModelFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Hardware Model filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Hardware Model"
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
      <FilterField label="Vendor Id">
        <Input
          aria-label="Vendor Id filter"
          placeholder="UUID"
          value={value.vendorId ?? ''}
          onChange={(v) => patch({ vendorId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Model Number">
        <Input
          aria-label="Model Number filter"
          value={value.modelNumber ?? ''}
          onChange={(v) => patch({ modelNumber: v || undefined })}
        />
      </FilterField>
      <FilterField label="Form Factor">
        <Input
          aria-label="Form Factor filter"
          value={value.formFactor ?? ''}
          onChange={(v) => patch({ formFactor: v || undefined })}
        />
      </FilterField>
      <FilterField label="Power Watts">
        <RangeInputs
          ariaLabel="Power Watts"
          min={value.powerWattsMin}
          max={value.powerWattsMax}
          onChange={(min, max) => patch({ powerWattsMin: min, powerWattsMax: max })}
        />
      </FilterField>
      <FilterField label="Eol Date">
        <DateRange
          ariaLabel="Eol Date"
          from={value.eolDateFrom}
          to={value.eolDateTo}
          onChange={(from, to) => patch({ eolDateFrom: from, eolDateTo: to })}
        />
      </FilterField>
      <FilterField label="Eos Date">
        <DateRange
          ariaLabel="Eos Date"
          from={value.eosDateFrom}
          to={value.eosDateTo}
          onChange={(from, to) => patch({ eosDateFrom: from, eosDateTo: to })}
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
          options={HARDWARE_MODEL_LIFECYCLE_STAGE_OPTIONS}
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

import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { TRANSCEIVER_FORM_FACTOR_OPTIONS, TRANSCEIVER_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { TransceiverFilter } from './types';

interface Props {
  value: TransceiverFilter;
  onChange: (next: TransceiverFilter) => void;
  onReset: () => void;
}

export function TransceiverFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: TransceiverFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Transceiver filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Transceiver"
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
      <FilterField label="Port Id">
        <Input
          aria-label="Port Id filter"
          placeholder="UUID"
          value={value.portId ?? ''}
          onChange={(v) => patch({ portId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Form Factor">
        <MultiSelect
          ariaLabel="Form Factor filter"
          options={TRANSCEIVER_FORM_FACTOR_OPTIONS}
          value={value.formFactor ?? []}
          onChange={(v) => patch({ formFactor: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Wavelength Nm">
        <RangeInputs
          ariaLabel="Wavelength Nm"
          min={value.wavelengthNmMin}
          max={value.wavelengthNmMax}
          onChange={(min, max) => patch({ wavelengthNmMin: min, wavelengthNmMax: max })}
        />
      </FilterField>
      <FilterField label="Reach Km">
        <RangeInputs
          ariaLabel="Reach Km"
          min={value.reachKmMin}
          max={value.reachKmMax}
          onChange={(min, max) => patch({ reachKmMin: min, reachKmMax: max })}
        />
      </FilterField>
      <FilterField label="Serial Number">
        <Input
          aria-label="Serial Number filter"
          value={value.serialNumber ?? ''}
          onChange={(v) => patch({ serialNumber: v || undefined })}
        />
      </FilterField>
      <FilterField label="Vendor Part">
        <Input
          aria-label="Vendor Part filter"
          value={value.vendorPart ?? ''}
          onChange={(v) => patch({ vendorPart: v || undefined })}
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
          options={TRANSCEIVER_LIFECYCLE_STAGE_OPTIONS}
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

import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { OPTICAL_CHANNEL_GRID_TYPE_OPTIONS, OPTICAL_CHANNEL_CRITICALITY_OPTIONS } from './types';
import type { OpticalChannelFilter } from './types';

interface Props {
  value: OpticalChannelFilter;
  onChange: (next: OpticalChannelFilter) => void;
  onReset: () => void;
}

export function OpticalChannelFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: OpticalChannelFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Optical Channel filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Optical Channel"
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
      <FilterField label="Wavelength Nm">
        <RangeInputs
          ariaLabel="Wavelength Nm"
          min={value.wavelengthNmMin}
          max={value.wavelengthNmMax}
          onChange={(min, max) => patch({ wavelengthNmMin: min, wavelengthNmMax: max })}
        />
      </FilterField>
      <FilterField label="Channel Number">
        <RangeInputs
          ariaLabel="Channel Number"
          min={value.channelNumberMin}
          max={value.channelNumberMax}
          onChange={(min, max) => patch({ channelNumberMin: min, channelNumberMax: max })}
        />
      </FilterField>
      <FilterField label="Grid Type">
        <MultiSelect
          ariaLabel="Grid Type filter"
          options={OPTICAL_CHANNEL_GRID_TYPE_OPTIONS}
          value={value.gridType ?? []}
          onChange={(v) => patch({ gridType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Bitrate Gbps">
        <RangeInputs
          ariaLabel="Bitrate Gbps"
          min={value.bitrateGbpsMin}
          max={value.bitrateGbpsMax}
          onChange={(min, max) => patch({ bitrateGbpsMin: min, bitrateGbpsMax: max })}
        />
      </FilterField>
      <FilterField label="System Id">
        <Input
          aria-label="System Id filter"
          placeholder="UUID"
          value={value.systemId ?? ''}
          onChange={(v) => patch({ systemId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={OPTICAL_CHANNEL_CRITICALITY_OPTIONS}
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

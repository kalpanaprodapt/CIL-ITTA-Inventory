import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { RADIO_UNIT_UNIT_TYPE_OPTIONS, RADIO_UNIT_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { RadioUnitFilter } from './types';

interface Props {
  value: RadioUnitFilter;
  onChange: (next: RadioUnitFilter) => void;
  onReset: () => void;
}

export function RadioUnitFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: RadioUnitFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Radio Unit filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Radio Unit"
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
      <FilterField label="Site Id">
        <Input
          aria-label="Site Id filter"
          placeholder="UUID"
          value={value.siteId ?? ''}
          onChange={(v) => patch({ siteId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Unit Type">
        <MultiSelect
          ariaLabel="Unit Type filter"
          options={RADIO_UNIT_UNIT_TYPE_OPTIONS}
          value={value.unitType ?? []}
          onChange={(v) => patch({ unitType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Band Mhz">
        <RangeInputs
          ariaLabel="Band Mhz"
          min={value.bandMhzMin}
          max={value.bandMhzMax}
          onChange={(min, max) => patch({ bandMhzMin: min, bandMhzMax: max })}
        />
      </FilterField>
      <FilterField label="Tx Power Dbm">
        <RangeInputs
          ariaLabel="Tx Power Dbm"
          min={value.txPowerDbmMin}
          max={value.txPowerDbmMax}
          onChange={(min, max) => patch({ txPowerDbmMin: min, txPowerDbmMax: max })}
        />
      </FilterField>
      <FilterField label="Serial Number">
        <Input
          aria-label="Serial Number filter"
          value={value.serialNumber ?? ''}
          onChange={(v) => patch({ serialNumber: v || undefined })}
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
          options={RADIO_UNIT_LIFECYCLE_STAGE_OPTIONS}
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

import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { WDM_SYSTEM_BAND_OPTIONS, WDM_SYSTEM_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { WdmSystemFilter } from './types';

interface Props {
  value: WdmSystemFilter;
  onChange: (next: WdmSystemFilter) => void;
  onReset: () => void;
}

export function WdmSystemFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: WdmSystemFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Wdm System filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Wdm System"
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
      <FilterField label="System Name">
        <Input
          aria-label="System Name filter"
          value={value.systemName ?? ''}
          onChange={(v) => patch({ systemName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Channel Capacity">
        <RangeInputs
          ariaLabel="Channel Capacity"
          min={value.channelCapacityMin}
          max={value.channelCapacityMax}
          onChange={(min, max) => patch({ channelCapacityMin: min, channelCapacityMax: max })}
        />
      </FilterField>
      <FilterField label="Band">
        <MultiSelect
          ariaLabel="Band filter"
          options={WDM_SYSTEM_BAND_OPTIONS}
          value={value.band ?? []}
          onChange={(v) => patch({ band: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Span Count">
        <RangeInputs
          ariaLabel="Span Count"
          min={value.spanCountMin}
          max={value.spanCountMax}
          onChange={(min, max) => patch({ spanCountMin: min, spanCountMax: max })}
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
          options={WDM_SYSTEM_LIFECYCLE_STAGE_OPTIONS}
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

import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { BACKHAUL_LINK_LINK_MEDIUM_OPTIONS } from './types';
import type { BackhaulLinkFilter } from './types';

interface Props {
  value: BackhaulLinkFilter;
  onChange: (next: BackhaulLinkFilter) => void;
  onReset: () => void;
}

export function BackhaulLinkFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: BackhaulLinkFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Backhaul Link filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Backhaul Link"
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
      <FilterField label="Base Station Id">
        <Input
          aria-label="Base Station Id filter"
          placeholder="UUID"
          value={value.baseStationId ?? ''}
          onChange={(v) => patch({ baseStationId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Link Medium">
        <MultiSelect
          ariaLabel="Link Medium filter"
          options={BACKHAUL_LINK_LINK_MEDIUM_OPTIONS}
          value={value.linkMedium ?? []}
          onChange={(v) => patch({ linkMedium: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Capacity Mbps">
        <RangeInputs
          ariaLabel="Capacity Mbps"
          min={value.capacityMbpsMin}
          max={value.capacityMbpsMax}
          onChange={(min, max) => patch({ capacityMbpsMin: min, capacityMbpsMax: max })}
        />
      </FilterField>
      <FilterField label="Latency Ms">
        <RangeInputs
          ariaLabel="Latency Ms"
          min={value.latencyMsMin}
          max={value.latencyMsMax}
          onChange={(min, max) => patch({ latencyMsMin: min, latencyMsMax: max })}
        />
      </FilterField>
      <FilterField label="Circuit Ref">
        <Input
          aria-label="Circuit Ref filter"
          value={value.circuitRef ?? ''}
          onChange={(v) => patch({ circuitRef: v || undefined })}
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
      <FilterField label="Labels">
        <Input
          aria-label="Labels filter"
          value={value.labels ?? ''}
          onChange={(v) => patch({ labels: v || undefined })}
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

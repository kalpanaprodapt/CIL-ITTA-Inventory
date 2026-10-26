import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { SLA_PROFILE_CRITICALITY_OPTIONS } from './types';
import type { SlaProfileFilter } from './types';

interface Props {
  value: SlaProfileFilter;
  onChange: (next: SlaProfileFilter) => void;
  onReset: () => void;
}

export function SlaProfileFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: SlaProfileFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Sla Profile filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Sla Profile"
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
      <FilterField label="Profile Name">
        <Input
          aria-label="Profile Name filter"
          value={value.profileName ?? ''}
          onChange={(v) => patch({ profileName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Availability Pct">
        <RangeInputs
          ariaLabel="Availability Pct"
          min={value.availabilityPctMin}
          max={value.availabilityPctMax}
          onChange={(min, max) => patch({ availabilityPctMin: min, availabilityPctMax: max })}
        />
      </FilterField>
      <FilterField label="Mttr Minutes">
        <RangeInputs
          ariaLabel="Mttr Minutes"
          min={value.mttrMinutesMin}
          max={value.mttrMinutesMax}
          onChange={(min, max) => patch({ mttrMinutesMin: min, mttrMinutesMax: max })}
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
      <FilterField label="Jitter Ms">
        <RangeInputs
          ariaLabel="Jitter Ms"
          min={value.jitterMsMin}
          max={value.jitterMsMax}
          onChange={(min, max) => patch({ jitterMsMin: min, jitterMsMax: max })}
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
          options={SLA_PROFILE_CRITICALITY_OPTIONS}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}

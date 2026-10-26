import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { TICKET_LINK_TICKET_SYSTEM_OPTIONS, TICKET_LINK_CRITICALITY_OPTIONS } from './types';
import type { TicketLinkFilter } from './types';

interface Props {
  value: TicketLinkFilter;
  onChange: (next: TicketLinkFilter) => void;
  onReset: () => void;
}

export function TicketLinkFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: TicketLinkFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Ticket Link filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Ticket Link"
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
      <FilterField label="Alarm Id">
        <Input
          aria-label="Alarm Id filter"
          placeholder="UUID"
          value={value.alarmId ?? ''}
          onChange={(v) => patch({ alarmId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Ticket System">
        <MultiSelect
          ariaLabel="Ticket System filter"
          options={TICKET_LINK_TICKET_SYSTEM_OPTIONS}
          value={value.ticketSystem ?? []}
          onChange={(v) => patch({ ticketSystem: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Ticket Number">
        <Input
          aria-label="Ticket Number filter"
          value={value.ticketNumber ?? ''}
          onChange={(v) => patch({ ticketNumber: v || undefined })}
        />
      </FilterField>
      <FilterField label="Linked At">
        <DateRange
          ariaLabel="Linked At"
          from={value.linkedAtFrom}
          to={value.linkedAtTo}
          onChange={(from, to) => patch({ linkedAtFrom: from, linkedAtTo: to })}
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
          options={TICKET_LINK_CRITICALITY_OPTIONS}
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
